package com.arycer.permadeathfabric.difficultyChanges.day10;

import com.google.common.collect.Iterables;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobSpawningRules {
    public void doubleMobcap(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 2);
    }

    HashMap<StatusEffect, Integer> effectList = new HashMap<>() {{
        put(StatusEffects.SPEED, 3); // speed
        put(StatusEffects.STRENGTH, 4); // strength
        put(StatusEffects.JUMP_BOOST, 5); // jump boost
        put(StatusEffects.GLOWING, 1); // glowing
        put(StatusEffects.REGENERATION, 4); // regeneration
        put(StatusEffects.RESISTANCE, 3); // resistance
        put(StatusEffects.INVISIBILITY, 1); // invisibility
        put(StatusEffects.SLOW_FALLING, 1); // slow falling
    }};

    public void summonOpSpider(SpiderEntity spider) {
        // pick a random number between 1 and 3
        int random = spider.getRandom().nextInt(3) + 1;

        // make a list for storing the effects so that the same effect doesn't get applied twice
        List<StatusEffect> effects = new ArrayList<>();

        // pick a random potion effect from the list for the random number of times and add it to the spider
        for (int i = 0; i < random; i++) {
            StatusEffect effect;
            do {
                effect = Iterables.get(effectList.keySet(), spider.getRandom().nextInt(effectList.size())); // pick a random effect from the list
            } while (effects.contains(effect)); // if the effect is already in the list, pick another one
            effects.add(effect); // add the effect to the list

            int duration = Integer.MAX_VALUE; // set the duration to the max value
            int amplifier = effectList.get(effect); // get the amplifier from the list
            boolean ambient = false; // set ambient to false
            boolean showParticles = true; // set showParticles to true
            boolean showIcon = true; // set showIcon to true

            StatusEffectInstance effectInstance = new StatusEffectInstance(effect, duration, amplifier, ambient, showParticles, showIcon); // create a new StatusEffectInstance
            spider.addStatusEffect(effectInstance); // add the effect to the spider
        }
    }

}
