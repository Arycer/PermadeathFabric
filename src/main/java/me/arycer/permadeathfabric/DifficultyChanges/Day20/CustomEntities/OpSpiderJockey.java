package me.arycer.permadeathfabric.DifficultyChanges.Day20.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import java.util.HashMap;

public class OpSpiderJockey {
    private static final HashMap<StatusEffect, Integer> effectList = new HashMap<>(){{
        put(StatusEffects.SPEED, 3);
        put(StatusEffects.STRENGTH, 4);
        put(StatusEffects.JUMP_BOOST, 5);
        put(StatusEffects.GLOWING, 1);
        put(StatusEffects.REGENERATION, 4);
        put(StatusEffects.RESISTANCE, 3);
        put(StatusEffects.INVISIBILITY, 1);
        put(StatusEffects.SLOW_FALLING, 1);
    }};

    public static void summon(SpiderEntity spider) {
        EntityUtils.addRandomEffectsFromList(spider, effectList, 3, 5);

        EntityType<?> type = spider.getType();
        if (type.equals(EntityType.CAVE_SPIDER)) return;

        switch (spider.getRandom().nextInt(5) + 1) {
            case 1 -> ChainWitherSkeleton.create(new WitherSkeletonEntity(EntityType.WITHER_SKELETON, spider.world)).startRiding(spider, true);
            case 2 -> DiamondSkeleton.create(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider, true);
            case 3 -> GoldSkeleton.create(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider, true);
            case 4 -> IronSkeleton.create(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider, true);
            case 5 -> LeatherWitherSkeleton.create(new WitherSkeletonEntity(EntityType.WITHER_SKELETON, spider.world)).startRiding(spider, true);
        }
    }
}