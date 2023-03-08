package me.arycer.permadeathfabric.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import java.util.Objects;

public class SetBanOnDeathCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("permadeath")
                .then(CommandManager.literal("banOnDeath")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(SetBanOnDeathCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> ctx) {
        var source = ctx.getSource();

        if (!source.hasPermissionLevel(2)) {
            var msg = Text.literal("No tienes permiso para ejecutar este comando.");
            msg.setStyle(msg.getStyle().withColor(net.minecraft.util.Formatting.RED));
            source.sendFeedback(msg, false);
            return 0;
        }

        var enabled = BoolArgumentType.getBool(ctx, "enabled");

        ModConfig.setBanOnDeath(enabled);

        var msg = Text.literal("Se ha establecido banOnDeath a " + enabled).setStyle(Style.EMPTY.withColor(net.minecraft.util.Formatting.GRAY));
        Objects.requireNonNull(source.getPlayer()).sendMessage(msg, false);
        return 0;
    }
}
