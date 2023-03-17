package me.arycer.permadeathfabric.DifficultyChanges.Day30.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DiamondSkeleton {
    public static SkeletonEntity create(SkeletonEntity skeleton) {
        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET); helmet.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack chestplate = new ItemStack(Items.DIAMOND_CHESTPLATE); chestplate.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS); leggings.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS); boots.addEnchantment(Enchantments.PROTECTION, 4);

        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.MakeUnbreakable(helmet));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.MakeUnbreakable(chestplate));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.MakeUnbreakable(leggings));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.MakeUnbreakable(boots));

        ItemStack axe = new ItemStack(Items.DIAMOND_AXE);
        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.MakeUnbreakable(axe));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);

        return EntityUtils.notDropEquipment(skeleton);
    }
}