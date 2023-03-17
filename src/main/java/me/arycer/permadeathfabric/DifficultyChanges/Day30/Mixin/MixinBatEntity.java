package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.Main;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.BatEntity;
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

@Mixin(BatEntity.class)
public class MixinBatEntity extends AmbientEntity {
    protected MixinBatEntity(EntityType<? extends AmbientEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void convertToBlaze(CallbackInfo ci) {
        if (ModConfig.getServerDay() < 30) return;
        BatEntity bat = (BatEntity) (Object) this;
        World world = bat.world;
        
        BlazeEntity blaze = new BlazeEntity(EntityType.BLAZE, bat.world);
        world.spawnEntity(blaze);

        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 2);
        blaze.addStatusEffect(effect);

        blaze.updatePosition(bat.getX(), bat.getY(), bat.getZ());
        bat.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        if (ModConfig.getServerDay() < 30) return super.canSpawn(world, spawnReason);
        AtomicInteger blazeCap = new AtomicInteger();
        Main.server.getPlayerManager().getPlayerList().forEach(player -> {
            int simulationDistance = 16 * Main.server.getSpawnRadius((ServerWorld) world);
            Box box = player.getBoundingBox().expand(simulationDistance);
            world.getEntitiesByType(EntityType.BLAZE, box, Entity::isAlive).forEach(entity -> blazeCap.getAndIncrement());
        });
        return blazeCap.get() < ModConfig.getServerDay();
    }
}