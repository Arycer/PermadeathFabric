package me.arycer.permadeath.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.arycer.permadeath.Util.ServerUtil.createText;

public class SetOpBanImmunity {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal(Main.MOD_ID)
                .then(CommandManager.literal("opBanImmunity")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(SetOpBanImmunity::run)))
        );
    }

    private static int run(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();

        boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
        ModConfig.setBanOnDeath(enabled);

        Text msg = createText(String.format("La inmunidad de los operadores al ban está %s.", enabled ? "activada" : "desactivada"), Formatting.GRAY);
        source.sendFeedback(msg, true);

        return 1;
    }
}