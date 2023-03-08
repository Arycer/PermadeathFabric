package me.arycer.permadeathfabric.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class SetDayCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("setDay")
                        .then(CommandManager.argument("day", IntegerArgumentType.integer())
                                .executes(SetDayCommand::run))));
    }

    private static int run (CommandContext<ServerCommandSource> ctx) {
        var source = ctx.getSource();

        if (!source.hasPermissionLevel(2)) {
            var msg = Text.literal("No tienes permiso para ejecutar este comando.").setStyle(Style.EMPTY.withColor(net.minecraft.util.Formatting.RED));
            source.sendFeedback(msg, false);
            return 0;
        }

        var day = IntegerArgumentType.getInteger(ctx, "day");
        ModConfig.setStartDay(day);

        var msg = Text.literal("Se ha establecido el día a " + day).setStyle(Style.EMPTY.withColor(net.minecraft.util.Formatting.GRAY));
        source.sendFeedback(msg, false);

        return 1;
    }
}
