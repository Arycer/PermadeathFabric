package me.arycer.permadeath.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeath.DifficultyChanges.Day20.CustomMobs.CustomSpider;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.mob.SpiderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class MixinSpiderEntity {
    @Inject(at = @At("TAIL"), method = "<init>")
    public void tick(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 20) return;

        CustomSpider.modifySpider((SpiderEntity) (Object) this);
    }
}