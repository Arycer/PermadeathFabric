package com.arycer.permadeathfabric.command;

import com.arycer.permadeathfabric.PermadeathConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SetDayCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("setDay")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("day", IntegerArgumentType.integer(1))
                                .executes(SetDayCommand::run))));
    }

    private static int run (CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        if (!source.hasPermissionLevel(2)) {
            var msg = Text.literal("No tienes permiso para ejecutar este comando.");
            msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.RED));
            source.sendFeedback(msg, false);
            return 0;
        }

        var day = IntegerArgumentType.getInteger(context, "day");
        long timestamp = System.currentTimeMillis() - ((long) (day - 1) * 24 * 60 * 60 * 1000);

        PermadeathConfig.setInitialTime(timestamp);
        PermadeathConfig.reload();

        var msg = Text.literal("Se ha establecido el día " + day);
        msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.GRAY));
        source.sendFeedback(msg, false);

        return 1;
    }
}