package me.arycer.permadeath.DifficultyChanges.Day30.End.Mixin;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.TntEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TntEntity.class)
public class MixinTntEntity {
    @Inject(at = @At("HEAD"), method = "explode", cancellable = true)
    public void explode(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

        TntEntity entity = (TntEntity) (Object) this;
        if (!entity.hasCustomName()) return;
        if (!Objects.requireNonNull(entity.getCustomName()).getString().equals("Shulker Explosivo")) return;

        ServerWorld world = (ServerWorld) entity.getEntityWorld();
        world.createExplosion(entity, entity.getX(), entity.getBodyY(0.0625), entity.getZ(), 15.0F, World.ExplosionSourceType.TNT);
        ci.cancel();
    }
}