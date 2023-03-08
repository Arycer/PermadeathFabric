package me.arycer.permadeathfabric.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.Objects;

public class GetDayCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("days")
                        .executes(GetDayCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> ctx) {
        var source = ctx.getSource();
        var serverDay = ModConfig.getServerDay();
        var msg = Text.literal("Estamos en el día " + serverDay).setStyle(Style.EMPTY.withColor(net.minecraft.util.Formatting.GRAY));
        Objects.requireNonNull(source.getPlayer()).sendMessage(msg, false);
        return 1;
    }
}
