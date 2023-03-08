package me.arycer.permadeathfabric.DifficultyChanges.Day20.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.NbtUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DiamondSkeleton {
    public static SkeletonEntity create(SkeletonEntity skeleton) {
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.MakeUnbreakable(new ItemStack(Items.DIAMOND_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.MakeUnbreakable(new ItemStack(Items.DIAMOND_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.MakeUnbreakable(new ItemStack(Items.DIAMOND_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.MakeUnbreakable(new ItemStack(Items.DIAMOND_BOOTS)));
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.MakeUnbreakable(new ItemStack(Items.BOW)));

        return EntityUtils.MobNotDropEquipment(skeleton);
    }
}