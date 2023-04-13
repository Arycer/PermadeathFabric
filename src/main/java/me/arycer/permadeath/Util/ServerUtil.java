package me.arycer.permadeath.Util;

import me.arycer.permadeath.Commands.*;
import me.arycer.permadeath.Main;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ServerUtil {
    public static void cacheServer() {
        ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
            Main.server = server;
        });
    }

    public static Text createText(String text, Formatting color) {
        return Text.literal(text).setStyle(Style.EMPTY.withColor(color));
    }

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(GetDay::register);
        CommandRegistrationCallback.EVENT.register(ReloadConfig::register);
        CommandRegistrationCallback.EVENT.register(SetBanOnDeath::register);
        CommandRegistrationCallback.EVENT.register(SetDay::register);
        CommandRegistrationCallback.EVENT.register(SetOpBanImmunity::register);
    }
}