package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.PassiveToAggressive;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AnimalEntity.class)
public abstract class MixinAnimalEntity extends PassiveEntity {
    protected MixinAnimalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
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