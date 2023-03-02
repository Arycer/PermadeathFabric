package com.arycer.permadeathfabric.command;

import com.arycer.permadeathfabric.PermadeathConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("reload")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(ReloadConfigCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        var source = serverCommandSourceCommandContext.getSource();

        if (!source.hasPermissionLevel(2)) {
            var msg = Text.literal("No tienes permiso para ejecutar este comando.");
            msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.RED));
            source.sendFeedback(msg, false);
            return 0;
        }

        PermadeathConfig.reload();

        var msg = Text.literal("Se ha recargado la configuración.");
        msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.GRAY));
        source.sendFeedback(msg, false);
        return 1;
    }
}