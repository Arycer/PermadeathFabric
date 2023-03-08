package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.ModifyLootTables;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity {
    @Inject(method = "getLootTable", at = @At("RETURN"), cancellable = true)
    private void register(CallbackInfoReturnable<Identifier> cir) {
        int day = ModConfig.getServerDay();
        if (day < 20) return;
        ModifyLootTables.register(cir, (MobEntity) (Object) this);
    }
}