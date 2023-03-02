package com.arycer.permadeathfabric;

import com.arycer.permadeathfabric.command.BanOnDeathCommand;
import com.arycer.permadeathfabric.command.DaysCommand;
import com.arycer.permadeathfabric.command.ReloadConfigCommand;
import com.arycer.permadeathfabric.command.SetDayCommand;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.arycer.permadeathfabric.difficultyChanges.day1.WorldGenerationRules;

@Environment(EnvType.SERVER)
public class PermadeathServer implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("PermadeathFabric");
    public static MinecraftServer server;

    private static void CacheServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> PermadeathServer.server = server);
    }

    private static void RegisterCommands() {
        CommandRegistrationCallback.EVENT.register(ReloadConfigCommand::register);
        CommandRegistrationCallback.EVENT.register(SetDayCommand::register);
        CommandRegistrationCallback.EVENT.register(DaysCommand::register);
        CommandRegistrationCallback.EVENT.register(BanOnDeathCommand::register);
    }

    WorldGenerationRules worldGenerationRules = new WorldGenerationRules();

    public static PermadeathConfig config = new PermadeathConfig();

    @Override
    public void onInitializeServer() {
        worldGenerationRules.removeAncientDebris();
        LOGGER.info("PermadeathFabric is loading");
        config.load();
        RegisterCommands();
        CacheServer();
    }
}