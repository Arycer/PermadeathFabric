package me.arycer.permadeathfabric.DifficultyChanges.Day10.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day10.CustomEntities.OpSpider;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class MixinSpiderEntity {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityType entityType, World world, CallbackInfo ci) {
        var day = ModConfig.getServerDay();
        if (day < 10 || day >= 20) return;
        OpSpider.summon((SpiderEntity) (Object) this);
    }
}
