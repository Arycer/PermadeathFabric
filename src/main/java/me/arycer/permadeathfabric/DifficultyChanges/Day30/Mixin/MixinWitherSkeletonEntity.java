package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day30.CustomEntities.*;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public abstract class MixinWitherSkeletonEntity extends AbstractSkeletonEntity {
    protected MixinWitherSkeletonEntity(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init (CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;
        WitherSkeletonEntity skeleton = (WitherSkeletonEntity) (Object) this;
        switch (skeleton.getRandom().nextInt(2)) {
            case 0 -> ChainWitherSkeleton.create(skeleton);
            case 1 -> LeatherWitherSkeleton.create(skeleton);
        }
    }

    @Override
    public void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if (ModConfig.getServerDay() < 30) super.initEquipment(random, localDifficulty);
    }
}
