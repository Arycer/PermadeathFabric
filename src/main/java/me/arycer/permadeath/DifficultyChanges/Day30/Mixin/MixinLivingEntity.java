package me.arycer.permadeath.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeath.DifficultyChanges.Day30.CustomMobs.CustomSkeletons;
import me.arycer.permadeath.DifficultyChanges.Day30.LootTables;
import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.EntityUtils;
import me.arycer.permadeath.Util.ModConfig;
import me.arycer.permadeath.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

import static me.arycer.permadeath.Util.ServerUtil.addHoverText;
import static me.arycer.permadeath.Util.ServerUtil.createText;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(at = @At("RETURN"), method = "getLootTable", cancellable = true)
    public void getLootTable(CallbackInfoReturnable<Identifier> cir) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof MobEntity mob)) return;
        LootTables.register(cir, mob);
    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void tryUseTotem(CallbackInfoReturnable<Boolean> cir) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.isPlayer()) return;

        boolean isTotemInHand = entity.getStackInHand(Hand.MAIN_HAND).isOf(Items.TOTEM_OF_UNDYING) || entity.getStackInHand(Hand.OFF_HAND).isOf(Items.TOTEM_OF_UNDYING);
        if (!isTotemInHand) return;

        Text text;
        int random = entity.getRandom().nextInt(100) + 1;
        if (random == 100) {
            text = createText(String.format("¡%s ha usado un tótem de la inmortalidad, pero ha fallado!", entity.getName().getString()), Formatting.GRAY, false);
            cir.setReturnValue(false);
        } else {
            text = createText(String.format("¡%s ha usado un tótem de la inmortalidad!", entity.getName().getString()), Formatting.GRAY, false);
        }

        Text hover = createText(String.format("Probabilidad: %d/100", random), Formatting.GRAY, false);
        Text finalText = addHoverText(text, hover);

        Main.LOGGER.info(String.format("%s ha usado un tótem de la inmortalidad. Probabilidad: %d/100", entity.getName().getString(), random));
        Main.server.getPlayerManager().broadcast(finalText, false);
    }

    @Unique
    private boolean converted = false;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 30 || converted) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        World world = entity.getEntityWorld();

        if (entity instanceof BatEntity) {
            BlazeEntity blaze = new BlazeEntity(EntityType.BLAZE, world);
            world.spawnEntity(blaze);

            blaze.updatePosition(entity.getX(), entity.getY(), entity.getZ());
            blaze.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 2));
            entity.remove(Entity.RemovalReason.DISCARDED);
        } else if (entity instanceof SquidEntity) {
            GuardianEntity guardian = new GuardianEntity(EntityType.GUARDIAN, world);
            world.spawnEntity(guardian);

            guardian.updatePosition(entity.getX(), entity.getY(), entity.getZ());
            guardian.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 2));
            entity.remove(Entity.RemovalReason.DISCARDED);
        } else if (entity instanceof SkeletonEntity skeleton && !entity.hasVehicle() && !skeleton.hasCustomName()) {
            int random = entity.getRandom().nextInt(5) + 1;

            ItemStack arrow = new ItemStack(Items.TIPPED_ARROW);
            arrow.getOrCreateNbt().putString("Potion", "minecraft:strong_harming");

            if (random <= 3) {
                switch (random) {
                    case 1 -> CustomSkeletons.diamondSkeleton(skeleton);
                    case 2 -> CustomSkeletons.ironSkeleton(skeleton);
                    case 3 -> CustomSkeletons.goldSkeleton(skeleton);
                }

                skeleton.equipStack(EquipmentSlot.OFFHAND, arrow);
                Text name = createText("Esqueleto con Clase", Formatting.GOLD, false);
                skeleton.setCustomName(name);
            } else {
                WitherSkeletonEntity witherSkeleton = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
                world.spawnEntity(witherSkeleton);

                witherSkeleton.updatePosition(entity.getX(), entity.getY(), entity.getZ());
                switch (random) {
                    case 4 -> CustomSkeletons.chainWitherSkeleton(witherSkeleton);
                    case 5 -> CustomSkeletons.leatherWitherSkeleton(witherSkeleton);
                }

                witherSkeleton.equipStack(EquipmentSlot.OFFHAND, arrow);
                Text name = createText("Esqueleto con Clase", Formatting.GOLD, false);
                witherSkeleton.setCustomName(name);
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
        } else if (entity instanceof CreeperEntity creeper) {
            NbtCompound tag = new NbtCompound();
            tag.putBoolean("powered", true);
            creeper.readCustomDataFromNbt(tag);
        } else if (entity instanceof PillagerEntity pillager) {
            ItemStack crossbow = new ItemStack(Items.CROSSBOW);
            crossbow.addEnchantment(Enchantments.QUICK_CHARGE, 5);
            pillager.equipStack(EquipmentSlot.MAINHAND, crossbow);
            pillager.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, -1));
        } else if (entity instanceof ZombifiedPiglinEntity piglin) {
            piglin.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_HELMET)));
            piglin.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_CHESTPLATE)));
            piglin.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_LEGGINGS)));
            piglin.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_BOOTS)));
            EntityUtils.notDropEquipment(piglin);
        } else if (entity instanceof IronGolemEntity golem) {
            golem.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 4));
        } else if (entity instanceof EndermanEntity enderman) {
            enderman.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 2));
        } else if (entity instanceof SilverfishEntity silverfish) {
            final HashMap<StatusEffect, Integer> EFFECT_LIST = new HashMap<>(){{
                put(StatusEffects.SPEED, 3);
                put(StatusEffects.STRENGTH, 4);
                put(StatusEffects.JUMP_BOOST, 5);
                put(StatusEffects.GLOWING, 1);
                put(StatusEffects.REGENERATION, 4);
                put(StatusEffects.RESISTANCE, 3);
                put(StatusEffects.INVISIBILITY, 1);
                put(StatusEffects.SLOW_FALLING, 1);
            }};

            EntityUtils.addListEffects(silverfish, EFFECT_LIST, 5, 5);
        }
        converted = true;
    }

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof ShulkerEntity)) return;

        World world = entity.getEntityWorld();
        BlockPos pos = entity.getBlockPos();

        TntEntity tnt = new TntEntity(EntityType.TNT, world);
        tnt.updatePosition(pos.getX(), pos.getY(), pos.getZ());
        tnt.setFuse(80);

        tnt.setCustomName(createText("Shulker Explosivo", Formatting.RED, false));

        world.spawnEntity(tnt);
    }
}