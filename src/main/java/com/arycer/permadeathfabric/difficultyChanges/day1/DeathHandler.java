package com.arycer.permadeathfabric.difficultyChanges.day1;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.ServerWorldProperties;
import java.util.Objects;

public class DeathHandler {
    private void makePillar(ServerPlayerEntity player) {
        var world = player.getWorld();

        world.setBlockState(player.getBlockPos().down(), Blocks.BEDROCK.getDefaultState()); // Set the block below the player to bedrock
        world.setBlockState(player.getBlockPos(), Blocks.NETHER_BRICK_FENCE.getDefaultState()); // Set the block the player is standing on to nether brick fence
        world.setBlockState(player.getBlockPos().up(), Blocks.PLAYER_HEAD.getDefaultState()); // Set the block above the player to a player head

        // Set the player's head to the player's skin
        SkullBlockEntity skullBlockEntity = (SkullBlockEntity) world.getBlockEntity(player.getBlockPos().up()); // Get the skull block entity
        assert skullBlockEntity != null; // Make sure the skull block entity is not null
        skullBlockEntity.setOwner(player.getGameProfile()); // Set the skull block entity's owner to the player's game profile
        skullBlockEntity.markDirty(); // Mark the skull block entity as dirty so it saves
    }

    private void announceDeath(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer(); // Get the server instance
        assert server != null; // Make sure the server instance is not null

        var playerName = player.getName().getString(); // Get the player's name
        var x = player.getBlockPos().getX(); // Get the player's x position
        var y = player.getBlockPos().getY(); // Get the player's y position
        var z = player.getBlockPos().getZ(); // Get the player's z position

        var msg1_1 = Text.literal("El comienzo del sufrimiento infinito de ").setStyle(Style.EMPTY.withBold(true).withColor(Formatting.RED)); // Create the first part of the message
        var msg1_2 = Text.literal(playerName).setStyle(Style.EMPTY.withBold(true).withColor(Formatting.DARK_RED)); // Create the second part of the message
        var msg1_3 = Text.literal(" ha comenzado. ¡HA SIDO PERMABANEADO!").setStyle(Style.EMPTY.withBold(true).withColor(Formatting.RED)); // Create the third part of the message
        var msg1 = msg1_1.append(msg1_2).append(msg1_3); // Append the first, second and third parts of the message

        server.getPlayerManager().broadcast(msg1, false); // Broadcast the message to all players

        var msg2 = Text.literal("El jugador " + playerName + " ha muerto en " + x + ", " + y + ", " + z); // Create the message
        msg2 = msg2.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GRAY))); // Set the message's style

        server.getPlayerManager().broadcast(msg2, false); // Broadcast the message to all players

        // play a sound to all players
        for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
            p.playSound(SoundEvents.ENTITY_BLAZE_DEATH, SoundCategory.PLAYERS, Float.MAX_VALUE, -0.1f);
        }

        // broadcast a title to all players
        var title = Text.literal("¡Permadeath!"); // Create the title
        var subtitle = Text.literal(playerName + " ha muerto"); // Create the subtitle

        title = title.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))); // Set the title's style
        subtitle = subtitle.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.WHITE))); // Set the subtitle's style

        for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
            p.networkHandler.sendPacket(new TitleS2CPacket(title)); // Send the title
            p.networkHandler.sendPacket(new SubtitleS2CPacket(subtitle)); // Send the subtitle
        }
    }

    private void banOnDeath(ServerPlayerEntity player) {
        var server = player.getServer(); // Get the server instance
        var playerProfile = player.getGameProfile(); // Get the player's name

        var msg = Text.literal("¡Has sido PERMABANEADO!"); // Create the message
        msg = msg.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))); // Set the message's style

        // ban the player with the reason msg
        PlayerManager playerManager = Objects.requireNonNull(server).getPlayerManager();

        playerManager.getUserBanList().add(new BannedPlayerEntry(playerProfile, null, null, null, msg.getString()));
        player.networkHandler.disconnect(msg);
    }

    private void DeathTrainManager(ServerPlayerEntity player) {
        var server = player.getServer(); // Get the server instance
        var world = player.getWorld(); // Get the player's world
        var registryKey = world.getRegistryKey(); // Get the world's registry key
        var serverWorld = Objects.requireNonNull(server).getWorld(registryKey); // Get the server's world
        var properties = (ServerWorldProperties) Objects.requireNonNull(serverWorld).getLevelProperties(); // Get the world's properties

        var serverDay = PermadeathConfig.getServerDay(); // Get the server day
        var thunderTimeToAdd = 3600 * 20 * serverDay; // Calculate the thunder time to add

        if (!properties.isThundering()) properties.setThunderTime(0); // Ensure the thunder time is 0 if the thunder is not active

        world.setWeather(0, properties.getThunderTime() + thunderTimeToAdd, true, true); // Prolong the thunder

        var msg = Text.literal("¡Comienza el Death Train con duración de " + serverDay + (serverDay == 1 ? " hora" : " horas") + "!"); // Create the message
        msg = msg.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))); // Set the message's style

        Objects.requireNonNull(server).getPlayerManager().broadcast(msg, false); // Broadcast the message to all players
    }

    public void DeathTrainTimer(ServerWorld world) {
        var server = world.getServer(); // Get the server instance
        var registryKey = world.getRegistryKey(); // Get the world's registry key
        var serverWorld = Objects.requireNonNull(server).getWorld(registryKey); // Get the server's world
        var properties = (ServerWorldProperties) Objects.requireNonNull(serverWorld).getLevelProperties(); // Get the world's properties

        if (!properties.isThundering()) return; // If it's not thundering, return

        // add an action bar message for all players
        var msg = Text.literal("Quedan " + ticksToTime(properties.getThunderTime()) + " de tormenta").setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        world.getPlayers().forEach(player -> player.sendMessage(msg, true));
    }

    public void playerDeath(ServerPlayerEntity player) {
        announceDeath(player); // Announce the player's death
        makePillar(player); // Make a pillar of skulls
        if (PermadeathConfig.getBanOnDeath()) banOnDeath(player); // Ban the player on death
        DeathTrainManager(player); // Start the Death Train
    }

    private static String ticksToTime(long ticks) {
        int seconds = (int) (ticks / 20);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


}
