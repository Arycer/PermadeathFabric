package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaiderEntity.class)
public class MixinRaiderEntity {
    @Unique private boolean initialized;
    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (ModConfig.getServerDay() < 30 || initialized) return;
        RaiderEntity pillager = (RaiderEntity) (Object) this;
        if (!pillager.getType().equals(EntityType.PILLAGER)) return;

        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE);
        pillager.addStatusEffect(effect);

        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.addEnchantment(Enchantments.QUICK_CHARGE, 10);

        pillager.equipStack(EquipmentSlot.MAINHAND, crossbow);
        initialized = true;
    }
}
