package me.arycer.permadeathfabric.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("reload")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(ReloadConfigCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> ctx) {
        var source = ctx.getSource();

        if (!source.hasPermissionLevel(2)) {
            var msg = Text.literal("No tienes permiso para ejecutar este comando.").setStyle(Style.EMPTY.withColor(net.minecraft.util.Formatting.RED));
            source.sendFeedback(msg, false);
            return 0;
        }

        ModConfig.reload();

        var msg = Text.literal("Se ha recargado la configuración.");
        msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.GRAY));
        source.sendFeedback(msg, false);
        return 1;
    }
}