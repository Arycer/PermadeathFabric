package me.arycer.permadeathfabric.DifficultyChanges.Day20.CustomEntities;

import net.minecraft.entity.mob.PhantomEntity;

public class OpPhantom {
    public static void summon (PhantomEntity phantom) {
        phantom.setHealth(phantom.getMaxHealth() * 2);
        phantom.setPhantomSize(9);
    }
}