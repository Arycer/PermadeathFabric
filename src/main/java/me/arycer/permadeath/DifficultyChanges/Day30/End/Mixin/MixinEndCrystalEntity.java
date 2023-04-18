package me.arycer.permadeath.DifficultyChanges.Day30.End.Mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.arycer.permadeath.Util.ServerUtil.isInDimension;

@Mixin(EndCrystalEntity.class)
public class MixinEndCrystalEntity {
    @Inject(at = @At("TAIL"), method = "crystalDestroyed")
    private void kill(CallbackInfo ci) {
        EndCrystalEntity entity = (EndCrystalEntity) (Object) this;
        ServerWorld world = (ServerWorld) entity.getEntityWorld();

        if (!isInDimension(entity, DimensionTypes.THE_END_ID)) return;

        BlockPos pos = entity.getBlockPos();
        if (world.getBlockState(pos.down()).equals(Blocks.BEDROCK.getDefaultState())) return;

        GhastEntity ghast = new GhastEntity(EntityType.GHAST, world);
        ghast.updatePosition(entity.getX(), entity.getY()+10, entity.getZ());
        world.spawnEntity(ghast);
    }
}
