package me.arycer.permadeath.Util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NbtUtils {
    public static ItemStack makeUnbreakable(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putBoolean("Unbreakable", true);
        stack.setNbt(tag);
        return stack;
    }

    public static ItemStack dyeArmor(ItemStack stack, int color) {
        NbtCompound tag = stack.getOrCreateNbt();
        NbtCompound display = tag.getCompound("display");
        display.putInt("color", color);
        tag.put("display", display);
        stack.setNbt(tag);
        return stack;
    }
}
