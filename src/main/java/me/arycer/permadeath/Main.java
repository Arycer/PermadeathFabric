package me.arycer.permadeath;

import me.arycer.permadeath.Util.ModConfig;
import me.arycer.permadeath.Util.ServerUtil;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.SERVER)
public class Main implements DedicatedServerModInitializer {
    public static final String MOD_ID = "permadeath";
    public static final Logger LOGGER = LogManager.getLogger("Permadeath");
    public static MinecraftServer server;
    public static ModConfig config = new ModConfig();

    @Override
    public void onInitializeServer() {
        ServerUtil.cacheServer();

        LOGGER.info("Permadeath está cargando. Idea original de ElRichMC, port a Fabric por Arycer.");
        config.load();

        ServerUtil.registerCommands();
    }
}