package me.arycer.permadeathfabric.DifficultyChanges.Day10.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpSpider {
    private static final HashMap<StatusEffect, Integer> effectList = new HashMap<StatusEffect, Integer>() {{
        put(StatusEffects.SPEED, 3); // speed
        put(StatusEffects.STRENGTH, 4); // strength
        put(StatusEffects.JUMP_BOOST, 5); // jump boost
        put(StatusEffects.GLOWING, 1); // glowing
        put(StatusEffects.REGENERATION, 4); // regeneration
        put(StatusEffects.RESISTANCE, 3); // resistance
        put(StatusEffects.INVISIBILITY, 1); // invisibility
        put(StatusEffects.SLOW_FALLING, 1); // slow falling
    }};

    public static void summon(SpiderEntity spider) {
        EntityUtils.AddRandomEffectsFromList(spider, effectList, 1, 3);
    }
}
