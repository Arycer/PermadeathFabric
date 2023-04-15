package me.arycer.permadeath.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PhantomEntity.class)
public abstract class MixinPhantomEntity extends FlyingEntity {
    @Shadow public abstract void setPhantomSize(int size);

    protected MixinPhantomEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int day = ModConfig.getServerDay();
        if (day >= 20) setPhantomSize(9);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}