package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.PassiveToAggressive;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SnowGolemEntity.class)
public abstract class MixinSnowGolemEntity extends GolemEntity {
    protected MixinSnowGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
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
