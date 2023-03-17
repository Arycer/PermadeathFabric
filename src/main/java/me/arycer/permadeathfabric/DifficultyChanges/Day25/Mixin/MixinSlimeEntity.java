package me.arycer.permadeathfabric.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(SlimeEntity.class)
public abstract class MixinSlimeEntity extends MobEntity {
    @Shadow public abstract void setSize(int size, boolean heal);

    protected MixinSlimeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int day = ModConfig.getServerDay();
        if (day >= 25) {
            setSize(15, true);
            EntityUtils.setMaxHealth(this, this.getMaxHealth() * 2, true);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}