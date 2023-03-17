package me.arycer.permadeathfabric.DifficultyChanges.Day20.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class GoldSkeleton {
    public static SkeletonEntity create (SkeletonEntity skeleton) {
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.addEnchantment(Enchantments.SHARPNESS, 20);

        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.MakeUnbreakable(crossbow));
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.MakeUnbreakable(new ItemStack(Items.GOLDEN_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.MakeUnbreakable(new ItemStack(Items.GOLDEN_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.MakeUnbreakable(new ItemStack(Items.GOLDEN_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.MakeUnbreakable(new ItemStack(Items.GOLDEN_BOOTS)));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);

        return EntityUtils.notDropEquipment(skeleton);
    }
}
