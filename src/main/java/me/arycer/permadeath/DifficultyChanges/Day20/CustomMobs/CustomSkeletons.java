package me.arycer.permadeath.DifficultyChanges.Day20.CustomMobs;

import me.arycer.permadeath.Util.EntityUtils;
import me.arycer.permadeath.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.awt.*;

public class CustomSkeletons {
    public static WitherSkeletonEntity chainWitherSkeleton (WitherSkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.CHAINMAIL_BOOTS)));

        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.PUNCH, 20);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(bow));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        return EntityUtils.notDropEquipment(skeleton);
    }

    public static SkeletonEntity diamondSkeleton (SkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.DIAMOND_BOOTS)));
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(new ItemStack(Items.BOW)));

        return EntityUtils.notDropEquipment(skeleton);
    }

    public static SkeletonEntity goldSkeleton (SkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.GOLDEN_BOOTS)));

        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.addEnchantment(Enchantments.SHARPNESS, 20);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(crossbow));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        return EntityUtils.notDropEquipment(skeleton);
    }

    public static SkeletonEntity ironSkeleton (SkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(new ItemStack(Items.IRON_BOOTS)));

        ItemStack axe = new ItemStack(Items.IRON_AXE);
        axe.addEnchantment(Enchantments.FIRE_ASPECT, 2);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(axe));

        return EntityUtils.notDropEquipment(skeleton);
    }

    public static WitherSkeletonEntity leatherWitherSkeleton (WitherSkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_HELMET), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_CHESTPLATE), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_LEGGINGS), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.makeUnbreakable(NbtUtils.dyeArmor(new ItemStack(Items.LEATHER_BOOTS), Color.RED.getRGB())));

        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.POWER, 20);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.makeUnbreakable(bow));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);
        return EntityUtils.notDropEquipment(skeleton);
    }
}