package me.arycer.permadeathfabric.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.RavagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RavagerEntity.class)
public class MixinRavagerEntity {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;
        RavagerEntity ravager = (RavagerEntity) (Object) this;

        StatusEffectInstance strength = new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 2);
        StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1);

        ravager.addStatusEffect(strength);
        ravager.addStatusEffect(speed);
    }
}
