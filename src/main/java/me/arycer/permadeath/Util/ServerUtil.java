package me.arycer.permadeath.Util;

import me.arycer.permadeath.Commands.*;
import me.arycer.permadeath.Main;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;

public class ServerUtil {
    public static void cacheServer() {
        ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> Main.server = server);
    }

    public static Text createText(String text, Formatting color, boolean bold) {
        return Text.literal(text).setStyle(Style.EMPTY.withColor(color).withBold(bold));
    }

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(GetDay::register);
        CommandRegistrationCallback.EVENT.register(ReloadConfig::register);
        CommandRegistrationCallback.EVENT.register(SetBanOnDeath::register);
        CommandRegistrationCallback.EVENT.register(SetDay::register);
        CommandRegistrationCallback.EVENT.register(SetOpBanImmunity::register);
    }

    public static ServerWorldProperties getServerWorldProperties() {
        MinecraftServer server = Main.server;
        assert server != null;

        World world = server.getWorld(World.OVERWORLD);
        assert world != null;

        RegistryKey<World> worldKey = world.getRegistryKey();
        ServerWorld serverWorld = server.getWorld(worldKey);
        assert serverWorld != null;

        return (ServerWorldProperties) serverWorld.getLevelProperties();
    }

    public static ServerWorld getOverworld() {
        MinecraftServer server = Main.server;
        assert server != null;

        World world = server.getWorld(World.OVERWORLD);
        assert world != null;

        RegistryKey<World> worldKey = world.getRegistryKey();
        ServerWorld serverWorld = server.getWorld(worldKey);
        assert serverWorld != null;

        return serverWorld;
    }
}