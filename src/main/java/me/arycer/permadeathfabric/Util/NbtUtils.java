package me.arycer.permadeathfabric.Util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NbtUtils {
    public static ItemStack MakeUnbreakable(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putBoolean("Unbreakable", true);
        stack.setNbt(tag);
        return stack;
    }

    public static ItemStack DyeStack (ItemStack stack, int color) {
        NbtCompound tag = stack.getOrCreateNbt();
        NbtCompound display = new NbtCompound();
        display.putInt("color", color);
        tag.put("display", display);
        stack.setNbt(tag);
        return stack;
    }
}
