package me.arycer.permadeathfabric.Util;

import me.arycer.permadeathfabric.Main;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;

public class WorldUtils {
    public static ServerWorldProperties getProperties() {
        assert Main.server != null;

        World world = Main.server.getWorld(World.OVERWORLD);
        assert world != null;

        RegistryKey<World> registryKey = world.getRegistryKey();
        World serverWorld = Main.server.getWorld(registryKey);
        assert serverWorld != null;

        return (ServerWorldProperties) serverWorld.getLevelProperties();
    }

    public static ServerWorld getOverworld() {
        assert Main.server != null;

        World world = Main.server.getWorld(World.OVERWORLD);
        assert world != null;

        RegistryKey<World> registryKey = world.getRegistryKey();
        ServerWorld serverWorld = Main.server.getWorld(registryKey);
        assert serverWorld != null;
        return serverWorld;
    }
}