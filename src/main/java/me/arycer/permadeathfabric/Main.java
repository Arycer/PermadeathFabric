package me.arycer.permadeathfabric;

import me.arycer.permadeathfabric.DifficultyChanges.Global.RemoveNaturalNetherite;
import me.arycer.permadeathfabric.Util.CommandLoader;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.SERVER)
public class Main implements DedicatedServerModInitializer {
    public static final String MOD_ID = "permadeathfabric";
    public static final Logger LOGGER = LogManager.getLogger("PermadeathFabric");
    public static MinecraftServer server;
    public static ModConfig config = new ModConfig();

    private static void CacheServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> Main.server = server);
    }

    @Override
    public void onInitializeServer() {
        LOGGER.info("PermadeathFabric is loading");
        config.load();
        CacheServer();

        RemoveNaturalNetherite.register();
        CommandLoader.registerCommands();
    }
}