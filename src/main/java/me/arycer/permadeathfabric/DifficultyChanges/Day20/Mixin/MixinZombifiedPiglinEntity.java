package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class MixinZombifiedPiglinEntity extends ZombieEntity {
    public MixinZombifiedPiglinEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    boolean isAggressive;

    @Override
    public void tick() {
        super.tick();
        int day = ModConfig.getServerDay();
        if (day >= 20 && !isAggressive) {
            super.initGoals();
            this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, (livingEntity) -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0D));
            isAggressive = true;
        }
    }
}