package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.PassiveToAggressive;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FishEntity.class)
public abstract class MixinFishEntity extends WaterCreatureEntity {
    protected MixinFishEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    boolean isAggressive;

    @Override
    public void tick() {
        super.tick();
        int day = ModConfig.getServerDay();
        if (day < 20 || isAggressive) return;
        super.initGoals();
        PassiveToAggressive.register(this.goalSelector, this);
        isAggressive = true;
    }
}