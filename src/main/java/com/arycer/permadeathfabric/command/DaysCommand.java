package com.arycer.permadeathfabric.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class DaysCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("days")
                        .executes(DaysCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        var source = serverCommandSourceCommandContext.getSource();
        var player = source.getPlayer();

        var msg = Text.literal("Estamos en el día " + com.arycer.permadeathfabric.PermadeathConfig.getServerDay());
        msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.GRAY));
        assert player != null;
        player.sendMessage(msg, false);
        return 1;
    }
}