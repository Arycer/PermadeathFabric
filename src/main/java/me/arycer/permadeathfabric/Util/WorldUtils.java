package me.arycer.permadeathfabric.Util;

import me.arycer.permadeathfabric.Main;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;

public class WorldUtils {
    public static ServerWorldProperties getProperties() {
        World world = Main.server.getWorld(World.OVERWORLD);
        assert world != null;

        RegistryKey<World> registryKey = world.getRegistryKey();
        World serverWorld = Main.server.getWorld(registryKey);
        assert serverWorld != null;

        return (ServerWorldProperties) serverWorld.getLevelProperties();
    }

    public static ServerWorld getOverworld() {
        World world = Main.server.getWorld(World.OVERWORLD);
        assert world != null;

        RegistryKey<World> registryKey = world.getRegistryKey();
        ServerWorld serverWorld = Main.server.getWorld(registryKey);
        assert serverWorld != null;
        return serverWorld;
    }
}