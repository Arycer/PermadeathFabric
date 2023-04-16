package me.arycer.permadeath.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(SquidEntity.class)
public class MixinSquidEntity extends WaterCreatureEntity {
    protected MixinSquidEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        if (ModConfig.getServerDay() < 30) return super.canSpawn(world, spawnReason);
        AtomicInteger guardianCap = new AtomicInteger();
        Main.server.getPlayerManager().getPlayerList().forEach(player -> {
            int simulationDistance = 16 * Main.server.getSpawnRadius((ServerWorld) world);
            Box box = player.getBoundingBox().expand(simulationDistance);
            world.getEntitiesByType(EntityType.GUARDIAN, box, Entity::isAlive).forEach(entity -> guardianCap.getAndIncrement());
        });
        return guardianCap.get() < ModConfig.getServerDay();
    }
}