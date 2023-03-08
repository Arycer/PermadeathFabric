package me.arycer.permadeathfabric.DifficultyChanges.Day20.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.awt.*;

public class LeatherWitherSkeleton {
    public static WitherSkeletonEntity create (WitherSkeletonEntity skeleton) {
        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.POWER, 20);

        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.MakeUnbreakable(bow));
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.MakeUnbreakable(NbtUtils.DyeStack(new ItemStack(Items.LEATHER_HELMET), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.MakeUnbreakable(NbtUtils.DyeStack(new ItemStack(Items.LEATHER_CHESTPLATE), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.MakeUnbreakable(NbtUtils.DyeStack(new ItemStack(Items.LEATHER_LEGGINGS), Color.RED.getRGB())));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.MakeUnbreakable(NbtUtils.DyeStack(new ItemStack(Items.LEATHER_BOOTS), Color.RED.getRGB())));

        skeleton.setHealth(skeleton.getMaxHealth() * 2);

        return EntityUtils.MobNotDropEquipment(skeleton);
    }
}
