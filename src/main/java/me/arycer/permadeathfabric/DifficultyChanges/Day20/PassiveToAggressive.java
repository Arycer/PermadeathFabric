package me.arycer.permadeathfabric.DifficultyChanges.Day20;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.ArrayList;
import java.util.List;

public class PassiveToAggressive {
    public static List <EntityType<? extends MobEntity>> list = new ArrayList<>() {{
        add(EntityType.COD); add(EntityType.SALMON); add(EntityType.PUFFERFISH);
        add(EntityType.TROPICAL_FISH); add(EntityType.DOLPHIN); add(EntityType.COW);
        add(EntityType.MOOSHROOM); add(EntityType.SHEEP); add(EntityType.PIG);
        add(EntityType.CHICKEN); add(EntityType.RABBIT); add(EntityType.HORSE);
        add(EntityType.DONKEY); add(EntityType.MULE); add(EntityType.SKELETON_HORSE);
        add(EntityType.ZOMBIE_HORSE); add(EntityType.LLAMA); add(EntityType.TRADER_LLAMA);
        add(EntityType.POLAR_BEAR); add(EntityType.PANDA); add(EntityType.BEE);
        add(EntityType.WOLF); add(EntityType.OCELOT); add(EntityType.CAT);
        add(EntityType.FOX); add(EntityType.BAT); add(EntityType.PARROT);
        add(EntityType.VILLAGER); add(EntityType.WANDERING_TRADER); add(EntityType.SNOW_GOLEM);
        add(EntityType.TURTLE); add(EntityType.STRIDER); add(EntityType.ALLAY);
        add(EntityType.AXOLOTL); add(EntityType.GOAT); add(EntityType.FROG);
        add(EntityType.GLOW_SQUID); add(EntityType.SQUID);
    }};

    public static void register(GoalSelector goalSelector, PathAwareEntity entity) {
        goalSelector.add(0, new MeleeAttackGoal(entity, 1.0D, false));
        goalSelector.add(0, new RevengeGoal(entity).setGroupRevenge());
        goalSelector.add(0, new ActiveTargetGoal<>(entity, PlayerEntity.class, true, false));
        goalSelector.add(0, new UniversalAngerGoal(entity, true));
    }
}