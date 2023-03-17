package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public class MixinCreeperEntity {
    @Unique private boolean isPowered;

    @Inject(method = "tick", at = @At("HEAD"))
    private void setPowered(CallbackInfo ci) {
        if (ModConfig.getServerDay() < 30 || isPowered) return;
        CreeperEntity creeper = (CreeperEntity) (Object) this;
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean("powered", true);
        creeper.readCustomDataFromNbt(nbt);
        isPowered = true;
    }
}
