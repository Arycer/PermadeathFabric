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

        var msg = Text.literal("¡No puedes dormir durante la tormenta!").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)));
        player.sendMessage(msg, false);
    }

    public void announceSleep(ServerPlayerEntity player) {
        var server = player.getServer(); // Get the server instance
        var world = player.getWorld(); // Get the player's world
        var registryKey = world.getRegistryKey(); // Get the world's registry key
        var serverWorld = Objects.requireNonNull(server).getWorld(registryKey); // Get the server's world
        var properties = (ServerWorldProperties) Objects.requireNonNull(serverWorld).getLevelProperties(); // Get the world's properties

        if (properties.isThundering()) return;

        var msg = player.getDisplayName().copy().append(Text.literal(" fue a dormir").setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));
        Objects.requireNonNull(server).getPlayerManager().broadcast(msg, false); // Broadcast the message to all players
    }
}
