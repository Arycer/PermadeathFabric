package com.arycer.permadeathfabric.command;

import com.arycer.permadeathfabric.PermadeathConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

public class BanOnDeathCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("banOnDeath")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(BanOnDeathCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        if (!source.hasPermissionLevel(2)) {
            var msg = Text.literal("No tienes permiso para ejecutar este comando.");
            msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.RED));
            source.sendFeedback(msg, false);
            return 0;
        }

        var enabled = BoolArgumentType.getBool(context, "enabled");

        PermadeathConfig.setBanOnDeath(enabled);
        PermadeathConfig.reload();

        var msg = Text.literal("Se ha establecido banOnDeath a " + enabled);
        msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.GRAY));
        Objects.requireNonNull(source.getPlayer()).sendMessage(msg, false);
        return 0;
    }
}