package me.arycer.permadeathfabric.DifficultyChanges.Day30.CustomEntities;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.NbtUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ChainWitherSkeleton {
    public static WitherSkeletonEntity create(WitherSkeletonEntity skeleton) {
        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.PUNCH, 30);
        bow.addEnchantment(Enchantments.POWER, 25);

        skeleton.equipStack(EquipmentSlot.MAINHAND, NbtUtils.MakeUnbreakable(bow));
        skeleton.equipStack(EquipmentSlot.HEAD, NbtUtils.MakeUnbreakable(new ItemStack(Items.CHAINMAIL_HELMET)));
        skeleton.equipStack(EquipmentSlot.CHEST, NbtUtils.MakeUnbreakable(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
        skeleton.equipStack(EquipmentSlot.LEGS, NbtUtils.MakeUnbreakable(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
        skeleton.equipStack(EquipmentSlot.FEET, NbtUtils.MakeUnbreakable(new ItemStack(Items.CHAINMAIL_BOOTS)));

        EntityUtils.setMaxHealth(skeleton, skeleton.getMaxHealth() * 2, true);

        return EntityUtils.notDropEquipment(skeleton);
    }
}
