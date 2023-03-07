package com.arycer.permadeathfabric.difficultyChanges.day20;

import com.arycer.permadeathfabric.Initializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;

public class LootTableModifiers {
    private static final List<EntityType> removedLootTable = List.of(
            EntityType.IRON_GOLEM,
            EntityType.MAGMA_CUBE,
            EntityType.PHANTOM,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ENDERMAN,
            EntityType.SLIME,
            EntityType.GHAST,
            EntityType.WITCH,
            EntityType.DROWNED,
            EntityType.GUARDIAN,
            EntityType.WITHER_SKELETON,
            EntityType.BLAZE,
            EntityType.EVOKER
    );
    public void register(CallbackInfoReturnable<Identifier> cir, MobEntity entity) {
        var type = entity.getType();
        if (removedLootTable.contains(type)) {
            Identifier emptyLootTableID = new Identifier(Initializer.MOD_ID, "entities/empty");
            cir.setReturnValue(emptyLootTableID);
        } else if (EntityType.RAVAGER.equals(type)) {
            Identifier RavagerLootTableID = new Identifier(Initializer.MOD_ID, "entities/ravager");
            cir.setReturnValue(RavagerLootTableID);
        }
    }
}
