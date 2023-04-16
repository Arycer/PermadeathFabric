package me.arycer.permadeath.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeath.Util.EntityUtils;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GhastEntity.class)
public class MixinGhastEntity {
    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;

        GhastEntity ghast = (GhastEntity) (Object) this;

        int random = ghast.getRandom().nextInt(21) + 40;
        EntityUtils.setMaxHealth(ghast, random, true);

        NbtCompound nbt = new NbtCompound();
        nbt.putByte("ExplosionPower", (byte) ((byte) ghast.getRandom().nextInt(3) + 3));
        ghast.readCustomDataFromNbt(nbt);
    }
}
