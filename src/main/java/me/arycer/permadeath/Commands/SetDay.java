package me.arycer.permadeath.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.arycer.permadeath.Util.ServerUtil.createText;

public class SetDay {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal(Main.MOD_ID)
                .then(CommandManager.literal("setDay")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("day", IntegerArgumentType.integer())
                                .executes(SetDay::run)))
        );
    }

    private static int run(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();

        int day = IntegerArgumentType.getInteger(ctx, "day");
        ModConfig.setServerDay(day);

        Text msg = createText(String.format("Se ha establecido el día actual a %d.", day), Formatting.GRAY);
        source.sendFeedback(msg, true);

        return 1;
    }
}