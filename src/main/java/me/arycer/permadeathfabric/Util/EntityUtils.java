package me.arycer.permadeathfabric.Util;

import com.google.common.collect.Iterables;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityUtils {
    public static <T extends MobEntity> T MobNotDropEquipment(T mob) {
        mob.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        mob.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
        mob.setEquipmentDropChance(EquipmentSlot.LEGS, 0);
        mob.setEquipmentDropChance(EquipmentSlot.FEET, 0);
        mob.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
        mob.setEquipmentDropChance(EquipmentSlot.OFFHAND, 0);

        return mob;
    }

    public static <T extends MobEntity> void AddRandomEffectsFromList (T mob, HashMap<StatusEffect, Integer> effectList, int minEffects, int maxEffects) {
        List<StatusEffect> effects = new ArrayList<>();
        int numEffects = (int) (Math.random() * (maxEffects - minEffects + 1) + minEffects);

        for (int i = 0; i < numEffects; i++) {
            StatusEffect effect;
            do {
                effect = Iterables.get(effectList.keySet(), mob.getRandom().nextInt(effectList.size())); // pick a random effect from the list
            } while (effects.contains(effect));
            effects.add(effect);

            int duration = Integer.MAX_VALUE;
            int amplifier = effectList.get(effect);
            boolean ambient = false;
            boolean showParticles = true;
            boolean showIcon = true;

            mob.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, ambient, showParticles, showIcon));
        }

    }
}
