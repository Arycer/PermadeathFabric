package me.arycer.permadeath.DifficultyChanges.Day25.CustomMobs;

import me.arycer.permadeath.DifficultyChanges.Day20.CustomMobs.CustomSkeletons;
import me.arycer.permadeath.Util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;

import java.util.HashMap;

public class CustomSpider {
    private static final HashMap<StatusEffect, Integer> EFFECT_LIST = new HashMap<>(){{
        put(StatusEffects.SPEED, 3);
        put(StatusEffects.STRENGTH, 4);
        put(StatusEffects.JUMP_BOOST, 5);
        put(StatusEffects.GLOWING, 1);
        put(StatusEffects.REGENERATION, 4);
        put(StatusEffects.RESISTANCE, 3);
        put(StatusEffects.INVISIBILITY, 1);
        put(StatusEffects.SLOW_FALLING, 1);
    }};

    public static void modifySpider(SpiderEntity spider) {
        EntityUtils.addListEffects(spider, EFFECT_LIST, 5, 5);

        if (spider instanceof CaveSpiderEntity) return;
        if (spider.hasPassengers()) spider.getPassengerList().forEach(p -> p.remove(Entity.RemovalReason.DISCARDED));

        switch (spider.getRandom().nextInt(5) + 1) {
            case 1 -> CustomSkeletons.chainWitherSkeleton(new WitherSkeletonEntity(EntityType.WITHER_SKELETON, spider.world)).startRiding(spider);
            case 2 -> CustomSkeletons.diamondSkeleton(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider);
            case 3 -> CustomSkeletons.ironSkeleton(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider);
            case 4 -> CustomSkeletons.goldSkeleton(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider);
            case 5 -> CustomSkeletons.leatherWitherSkeleton(new WitherSkeletonEntity(EntityType.WITHER_SKELETON, spider.world)).startRiding(spider);
        }
    }
}
