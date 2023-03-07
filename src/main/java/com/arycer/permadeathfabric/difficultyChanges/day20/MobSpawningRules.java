package com.arycer.permadeathfabric.difficultyChanges.day20;

import com.google.common.collect.Iterables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobSpawningRules {
    private static ItemStack MakeUnbreakable(ItemStack item) {
        NbtCompound tag = item.getOrCreateNbt();
        tag.putBoolean("Unbreakable", true);
        item.setNbt(tag);
        return item;
    }

    private static ItemStack DyeRed(ItemStack item) {
        NbtCompound tag = item.getOrCreateNbt();
        NbtCompound color = new NbtCompound();
        color.putInt("color", Color.RED.getRGB());
        tag.put("display", color);
        item.setNbt(tag);
        return item;
    }

    private static <T extends MobEntity> T MobNotDropEquipment(T mob) {
        // make the mob not drop equipment
        mob.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        mob.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
        mob.setEquipmentDropChance(EquipmentSlot.LEGS, 0);
        mob.setEquipmentDropChance(EquipmentSlot.FEET, 0);
        mob.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);

        return mob;
    }

    private SkeletonEntity DIAMOND_SKELETON (SkeletonEntity skeleton) {
        // give the skeleton diamond armor
        skeleton.equipStack(EquipmentSlot.HEAD, MakeUnbreakable(new ItemStack(Items.DIAMOND_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, MakeUnbreakable(new ItemStack(Items.DIAMOND_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, MakeUnbreakable(new ItemStack(Items.DIAMOND_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, MakeUnbreakable(new ItemStack(Items.DIAMOND_BOOTS)));

        // give the skeleton a bow
        skeleton.equipStack(EquipmentSlot.MAINHAND, MakeUnbreakable(new ItemStack(Items.BOW)));
        return MobNotDropEquipment(skeleton);
    }

    private SkeletonEntity IRON_SKELETON_AXE (SkeletonEntity skeleton) {
        // create the iron axe with fire aspect 2 enchantment
        ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
        ironAxe.addEnchantment(Enchantments.FIRE_ASPECT, 2);

        // give the skeleton the iron axe
        skeleton.equipStack(EquipmentSlot.MAINHAND, MakeUnbreakable(ironAxe));


        // give the skeleton full iron armor
        skeleton.equipStack(EquipmentSlot.HEAD, MakeUnbreakable(new ItemStack(Items.IRON_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, MakeUnbreakable(new ItemStack(Items.IRON_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, MakeUnbreakable(new ItemStack(Items.IRON_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, MakeUnbreakable(new ItemStack(Items.IRON_BOOTS)));

        return MobNotDropEquipment(skeleton);
    }

    private SkeletonEntity GOLD_SKELETON_CROSSBOW (SkeletonEntity skeleton) {
        // create a crossbow with sharpness 20
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.addEnchantment(Enchantments.SHARPNESS, 20);

        // give the skeleton the crossbow
        skeleton.equipStack(EquipmentSlot.MAINHAND, MakeUnbreakable(crossbow));

        // give the skeleton full gold armor
        skeleton.equipStack(EquipmentSlot.HEAD, MakeUnbreakable(new ItemStack(Items.GOLDEN_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, MakeUnbreakable(new ItemStack(Items.GOLDEN_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, MakeUnbreakable(new ItemStack(Items.GOLDEN_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, MakeUnbreakable(new ItemStack(Items.GOLDEN_BOOTS)));

        // duplicate skeleton health points
        skeleton.setHealth(skeleton.getMaxHealth() * 2);
        return MobNotDropEquipment(skeleton);
    }

    private WitherSkeletonEntity CHAIN_SKELETON_PUNCH (WitherSkeletonEntity skeleton) {
        // create a bow with punch 20
        ItemStack bow = MakeUnbreakable(new ItemStack(Items.BOW));
        bow.addEnchantment(Enchantments.PUNCH, 20);

        // give the skeleton the bow
        skeleton.equipStack(EquipmentSlot.MAINHAND, bow);

        // give the skeleton full chain armor
        skeleton.equipStack(EquipmentSlot.HEAD, MakeUnbreakable(new ItemStack(Items.CHAINMAIL_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, MakeUnbreakable(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, MakeUnbreakable(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, MakeUnbreakable(new ItemStack(Items.CHAINMAIL_BOOTS)));

        // duplicate skeleton health points
        skeleton.setHealth(skeleton.getMaxHealth() * 2);

        return MobNotDropEquipment(skeleton);
    }

    private WitherSkeletonEntity LEATHER_SKELETON_POWER (WitherSkeletonEntity skeleton) {
        // create a bow with power 20
        ItemStack bow = MakeUnbreakable(new ItemStack(Items.BOW));
        bow.addEnchantment(Enchantments.POWER, 20);

        // give the skeleton the bow
        skeleton.equipStack(EquipmentSlot.MAINHAND, bow);

        // give the skeleton full leather armor
        skeleton.equipStack(EquipmentSlot.HEAD, DyeRed(MakeUnbreakable(new ItemStack(Items.LEATHER_HELMET))));
        skeleton.equipStack(EquipmentSlot.CHEST, DyeRed(MakeUnbreakable(new ItemStack(Items.LEATHER_CHESTPLATE))));
        skeleton.equipStack(EquipmentSlot.LEGS, DyeRed(MakeUnbreakable(new ItemStack(Items.LEATHER_LEGGINGS))));
        skeleton.equipStack(EquipmentSlot.FEET, DyeRed(MakeUnbreakable(new ItemStack(Items.LEATHER_BOOTS))));

        // duplicate skeleton health points
        skeleton.setHealth(skeleton.getMaxHealth() * 2);

        return MobNotDropEquipment(skeleton);
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
        // pick a random number between 3 and 5
        int random = spider.getRandom().nextInt(3) + 3;

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

        switch (spider.getRandom().nextInt(5) + 1) {
            case 1 -> this.DIAMOND_SKELETON(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider, true);
            case 2 -> this.GOLD_SKELETON_CROSSBOW(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider, true);
            case 3 -> this.CHAIN_SKELETON_PUNCH(new WitherSkeletonEntity(EntityType.WITHER_SKELETON, spider.world)).startRiding(spider, true);
            case 4 -> this.LEATHER_SKELETON_POWER(new WitherSkeletonEntity(EntityType.WITHER_SKELETON, spider.world)).startRiding(spider, true);
            case 5 -> this.IRON_SKELETON_AXE(new SkeletonEntity(EntityType.SKELETON, spider.world)).startRiding(spider, true);
        }
    }

    public void summonOpPhantom(PhantomEntity phantomEntity) {
        phantomEntity.setHealth(phantomEntity.getHealth() * 2);
        phantomEntity.setPhantomSize(9);
    }
}