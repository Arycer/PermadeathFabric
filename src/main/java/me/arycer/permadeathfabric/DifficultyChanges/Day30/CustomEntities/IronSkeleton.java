package me.arycer.permadeathfabric.DifficultyChanges.Day30.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class IronSkeleton {
    public static SkeletonEntity create(SkeletonEntity skeleton) {
        ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
        ironAxe.addEnchantment(Enchantments.FIRE_ASPECT, 10);

        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.MakeUnbreakable(ironAxe));
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.MakeUnbreakable(new ItemStack(Items.IRON_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.MakeUnbreakable(new ItemStack(Items.IRON_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.MakeUnbreakable(new ItemStack(Items.IRON_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.MakeUnbreakable(new ItemStack(Items.IRON_BOOTS)));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);

        return EntityUtils.notDropEquipment(skeleton);
    }
}
