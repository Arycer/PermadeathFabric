package me.arycer.permadeath.DifficultyChanges.Day30.CustomMobs;

import me.arycer.permadeath.Util.EntityUtils;
import me.arycer.permadeath.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.awt.*;

public class CustomSkeletons {
    public static void chainWitherSkeleton (WitherSkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_BOOTS)));

        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.PUNCH, 30);
        bow.addEnchantment(Enchantments.POWER, 25);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(bow));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        EntityUtils.notDropEquipment(skeleton);
    }

    public static void diamondSkeleton (SkeletonEntity skeleton) {
        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET); helmet.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack chestplate = new ItemStack(Items.DIAMOND_CHESTPLATE); chestplate.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS); leggings.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS); boots.addEnchantment(Enchantments.PROTECTION, 4);

        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(helmet));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(chestplate));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(leggings));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(boots));

        ItemStack axe = new ItemStack(Items.DIAMOND_AXE);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(axe));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        EntityUtils.notDropEquipment(skeleton);
    }

    public static void goldSkeleton (SkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_BOOTS)));

        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.addEnchantment(Enchantments.SHARPNESS, 25);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(crossbow));

        skeleton.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 2));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        EntityUtils.notDropEquipment(skeleton);
    }

    public static void ironSkeleton (SkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_BOOTS)));

        ItemStack axe = new ItemStack(Items.IRON_AXE);
        axe.addEnchantment(Enchantments.FIRE_ASPECT, 10);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(axe));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        EntityUtils.notDropEquipment(skeleton);
    }

    public static void leatherWitherSkeleton (WitherSkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_HELMET), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_CHESTPLATE), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_LEGGINGS), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_BOOTS), Color.RED.getRGB())));

        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.POWER, 50);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(bow));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        EntityUtils.notDropEquipment(skeleton);
    }
}