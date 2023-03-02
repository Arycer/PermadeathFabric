package com.arycer.permadeathfabric.difficultyChanges.day1;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

public class SleepingBehaviour {
    public void deathTrainSleepDisabled(CallbackInfo ci, ServerPlayerEntity player) {
        var server = player.getServer(); // Get the server instance
        var world = player.getWorld(); // Get the player's world
        var registryKey = world.getRegistryKey(); // Get the world's registry key
        var serverWorld = Objects.requireNonNull(server).getWorld(registryKey); // Get the server's world
        var properties = (ServerWorldProperties) Objects.requireNonNull(serverWorld).getLevelProperties(); // Get the world's properties

        if (!properties.isThundering()) return;

        ci.cancel();

        var msg = Text.literal("¡No puedes dormir durante la tormenta!"); // Create the message
        msg = msg.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))); // Set the message's style

        player.sendMessage(msg, false);
    }

    public void announceSleep(ServerPlayerEntity player) {
        var server = player.getServer(); // Get the server instance
        var world = player.getWorld(); // Get the player's world
        var registryKey = world.getRegistryKey(); // Get the world's registry key
        var serverWorld = Objects.requireNonNull(server).getWorld(registryKey); // Get the server's world
        var properties = (ServerWorldProperties) Objects.requireNonNull(serverWorld).getLevelProperties(); // Get the world's properties

        if (properties.isThundering()) return;

        var msg_1 = Text.literal("El jugador ").setStyle(Style.EMPTY.withColor(Formatting.WHITE)); // Create the first part of the message
        var msg_2 = Text.literal(player.getEntityName()).setStyle(Style.EMPTY.withColor(Formatting.YELLOW)); // Create the second part of the message
        var msg_3 = Text.literal(" ha ido a dormir").setStyle(Style.EMPTY.withColor(Formatting.WHITE)); // Create the second part of the message
        var msg = msg_1.append(msg_2).append(msg_3); // Append the three parts of the message

        Objects.requireNonNull(server).getPlayerManager().broadcast(msg, false); // Broadcast the message to all players
    }
}
