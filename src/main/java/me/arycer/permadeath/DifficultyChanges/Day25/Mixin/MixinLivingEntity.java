package me.arycer.permadeath.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeath.DifficultyChanges.Day25.NetheriteMobs;
import me.arycer.permadeath.Util.EntityUtils;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.arycer.permadeath.Util.ServerUtil.getServerWorldProperties;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(at = @At("RETURN"), method = "tick")
    public void tick(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isPlayer()) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;

            ItemStack helmet = player.getInventory().player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chestplate = player.getInventory().player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack leggings = player.getInventory().player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack boots = player.getInventory().player.getEquippedStack(EquipmentSlot.FEET);

            int netheritePieces = 0;
            if (helmet.getItem().equals(Items.NETHERITE_HELMET)) netheritePieces++;
            if (chestplate.getItem().equals(Items.NETHERITE_CHESTPLATE)) netheritePieces++;
            if (leggings.getItem().equals(Items.NETHERITE_LEGGINGS)) netheritePieces++;
            if (boots.getItem().equals(Items.NETHERITE_BOOTS)) netheritePieces++;

            EntityUtils.setMaxHealth(player, netheritePieces == 4 ? 28 : 20, false);
            if (netheritePieces < 4 && player.getHealth() > 20) player.setHealth(20);
        } else {
            ServerWorldProperties properties = getServerWorldProperties();
            if (!properties.isThundering()) return;

            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1));
        }
    }

    @Inject(method = "getLootTable", at = @At("RETURN"), cancellable = true)
    public void getLootTable(CallbackInfoReturnable<Identifier> cir) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;

        if (!(((LivingEntity) (Object) this) instanceof MobEntity mob)) return;
        NetheriteMobs.register(cir, mob);
    }
}
