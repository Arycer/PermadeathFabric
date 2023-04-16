package me.arycer.permadeath.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MagmaCubeEntity.class)
public abstract class MixinMagmaCubeEntity extends MobEntity {
    protected MixinMagmaCubeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void setSize(int size, boolean heal);

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int day = ModConfig.getServerDay();
        if (day >= 25) setSize(16, true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}