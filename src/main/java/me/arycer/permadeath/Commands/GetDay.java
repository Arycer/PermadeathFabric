package me.arycer.permadeath.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

import static me.arycer.permadeath.Util.ServerUtil.createText;

public class GetDay {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal(Main.MOD_ID)
                .then(CommandManager.literal("days")
                        .executes(GetDay::run))
        );
    }

    private static int run(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();
        int day = ModConfig.getServerDay();

        Text msg = createText(String.format("El día actual es %d.", day), Formatting.GRAY);
        source.sendFeedback(msg, false);

        return 1;
    }
}