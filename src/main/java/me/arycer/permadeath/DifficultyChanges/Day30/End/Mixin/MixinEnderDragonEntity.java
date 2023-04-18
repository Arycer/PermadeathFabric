package me.arycer.permadeath.DifficultyChanges.Day30.End.Mixin;

import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ServerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;

import static me.arycer.permadeath.Util.ServerUtil.createText;

@Mixin(EnderDragonEntity.class)
public class MixinEnderDragonEntity {
    @Unique private boolean firstPhaseStarted = false;
    @Unique private boolean enragedPhaseStarted = false;
    @Unique private int ticksUntilLightningAttack = 0;

    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo ci) {
        nameHandler();
        manageTimings();
    }

    private void nameHandler() {
        EnderDragonEntity entity = (EnderDragonEntity) (Object) this;
        if (!firstPhaseStarted) {
            Text name = createText("PERMADEATH DEMON", Formatting.GOLD, true);
            entity.setCustomName(name);
            firstPhaseStarted = true;
        } else if (!enragedPhaseStarted && entity.getHealth() < entity.getMaxHealth() / 2) {
            Text name = createText("ENRAGED PERMADEATH DEMON", Formatting.GOLD, true);
            entity.setCustomName(name);
            enragedPhaseStarted = true;
        }
    }

    private BlockPos getSurfacePos(LivingEntity entity) {
        BlockPos pos = entity.getBlockPos();
        while (entity.getEntityWorld().getBlockState(pos).isAir()) {
            pos = pos.down();
        }
        return pos.up();
    }

    private BlockPos getRandomPositionAround(LivingEntity entity, int radius) {
        BlockPos pos = entity.getBlockPos();
        int random_x = entity.getRandom().nextInt(radius);
        int random_z = entity.getRandom().nextInt(radius);
        return pos.add(random_x, 0, random_z);
    }

    private void lightningAttack() {
        Stream<ServerPlayerEntity> playersInEnd = ServerUtil.getDimensionPlayers(DimensionTypes.THE_END_ID);
        List<ServerPlayerEntity> players = playersInEnd.toList();

        EnderDragonEntity entity = (EnderDragonEntity) (Object) this;
        int random = entity.getRandom().nextInt(players.size());
        ServerPlayerEntity player = players.get(random);

        BlockPos pos = getRandomPositionAround(player, 7);

        ServerWorld world = (ServerWorld) entity.getEntityWorld();
        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.updatePosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(lightning);

        ticksUntilLightningAttack = 5 * 20;
    }

    private void manageTimings() {
        if (ticksUntilLightningAttack > 0) {
            ticksUntilLightningAttack--;
        } else {
            lightningAttack();
        }
    }
}
