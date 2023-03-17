package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.Main;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(SquidEntity.class)
public class MixinSquidEntity extends WaterCreatureEntity {
    protected MixinSquidEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void convertToGuardian(CallbackInfo ci) {
        if (ModConfig.getServerDay() < 30) return;
        SquidEntity squid = (SquidEntity) (Object) this;
        World world = squid.world;

        GuardianEntity guardian = new GuardianEntity(EntityType.GUARDIAN, squid.world);
        world.spawnEntity(guardian);

        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 2);
        guardian.addStatusEffect(effect);

        guardian.updatePosition(squid.getX(), squid.getY(), squid.getZ());
        squid.remove(Entity.RemovalReason.DISCARDED);
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