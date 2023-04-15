package me.arycer.permadeath.DifficultyChanges.Day10.CustomMobs;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;

import java.util.HashMap;

import static me.arycer.permadeath.Util.EntityUtils.addListEffects;

public class CustomSpider {
    private static final HashMap<StatusEffect, Integer> EFFECT_LIST = new HashMap<StatusEffect, Integer>() {{
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
        addListEffects(spider, EFFECT_LIST, 1, 3);
    }
}
