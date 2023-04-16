package me.arycer.permadeath.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeath.DifficultyChanges.Day30.CustomMobs.CustomSkeletons;
import me.arycer.permadeath.DifficultyChanges.Day30.LootTables;
import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.arycer.permadeath.Util.ServerUtil.addHoverText;
import static me.arycer.permadeath.Util.ServerUtil.createText;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(at = @At("RETURN"), method = "getLootTable")
    public void getLootTable(CallbackInfoReturnable<Identifier> cir) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

        if (!(((LivingEntity) (Object) this) instanceof MobEntity mob)) return;
        LootTables.register(cir, mob);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(EntityType<? extends MobEntity> entityType, World world, CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        if (entityType.equals(EntityType.CREEPER)) {
            NbtCompound tag = new NbtCompound();
            tag.putBoolean("powered", true);
            entity.readCustomDataFromNbt(tag);
        } else if (entityType.equals(EntityType.PILLAGER)) {
            ItemStack crossbow = new ItemStack(Items.CROSSBOW);
            crossbow.addEnchantment(Enchantments.QUICK_CHARGE, 10);
            entity.equipStack(EquipmentSlot.MAINHAND, crossbow);

            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, -1));
        }
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
            converted = true;
        } else if (entity instanceof SquidEntity) {
            GuardianEntity guardian = new GuardianEntity(EntityType.GUARDIAN, world);
            world.spawnEntity(guardian);

            guardian.updatePosition(entity.getX(), entity.getY(), entity.getZ());
            guardian.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 2));
            entity.remove(Entity.RemovalReason.DISCARDED);
            converted = true;
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
        }
    }
}