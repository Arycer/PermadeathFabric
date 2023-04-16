package me.arycer.permadeath.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.RavagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RavagerEntity.class)
public class MixinRavagerEntity {
    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;

        RavagerEntity ravager = (RavagerEntity) (Object) this;

        ravager.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 2));
        ravager.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 1));
    }
}
