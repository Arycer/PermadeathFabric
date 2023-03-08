package me.arycer.permadeathfabric.DifficultyChanges.Global;

import com.mojang.authlib.GameProfile;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class DeathHandler {
    private static void makePillar(ServerPlayerEntity player) {
        World worldUtils = player.getWorld();

        worldUtils.setBlockState(player.getBlockPos().up(), Blocks.PLAYER_HEAD.getDefaultState());
        worldUtils.setBlockState(player.getBlockPos(), Blocks.NETHER_BRICK_FENCE.getDefaultState());
        worldUtils.setBlockState(player.getBlockPos().down(), Blocks.BEDROCK.getDefaultState());

        // Set the player's head to the player's skin
        SkullBlockEntity skullBlockEntity = (SkullBlockEntity) worldUtils.getBlockEntity(player.getBlockPos().up()); // Get the skull block entity
        assert skullBlockEntity != null; // Make sure the skull block entity is not null
        skullBlockEntity.setOwner(player.getGameProfile()); // Set the skull block entity's owner to the player's game profile
        skullBlockEntity.markDirty(); // Mark the skull block entity as dirty so it saves
    }

    private static void announceDeath(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer(); // Get the server instance
        assert server != null; // Make sure the server instance is not null

        String playerName = player.getName().getString(); // Get the player's name
        int x = player.getBlockPos().getX(); // Get the player's x position
        int y = player.getBlockPos().getY(); // Get the player's y position
        int z = player.getBlockPos().getZ(); // Get the player's z position

        Text msg1 = Text.literal("El comienzo del sufrimiento infinito de ").setStyle(Style.EMPTY.withColor(Formatting.RED).withBold(true))
                .append(playerName).setStyle(Style.EMPTY.withColor(Formatting.DARK_RED).withBold(true))
                .append(Text.literal(" ha comenzado. ¡HA SIDO PERMABANEADO!").setStyle(Style.EMPTY.withColor(Formatting.RED).withBold(true)));

        server.getPlayerManager().broadcast(msg1, false); // Broadcast the message to all players

        Text msg2 = Text.literal("El jugador " + playerName + " ha muerto en X: " + x + " Y: " + y + " Z: " + z).setStyle(Style.EMPTY.withColor(Formatting.GRAY));

        server.getPlayerManager().broadcast(msg2, false); // Broadcast the message to all players

        // Play a sound to all players
        server.getPlayerManager().getPlayerList().forEach(p -> p.playSound(SoundEvents.ENTITY_BLAZE_DEATH, SoundCategory.PLAYERS, Float.MAX_VALUE, -0.1f));

        // Broadcast a title to all players
        Text title = Text.literal("¡Permadeath!").setStyle(Style.EMPTY.withColor(Formatting.RED));
        Text subtitle = Text.literal(playerName + " ha muerto.").setStyle(Style.EMPTY.withColor(Formatting.WHITE));

        server.getPlayerManager().getPlayerList().forEach(p -> {
            p.networkHandler.sendPacket(new TitleS2CPacket(title));
            p.networkHandler.sendPacket(new SubtitleS2CPacket(subtitle));
        });
    }

    private static void banOnDeath(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer(); // Get the server instance
        assert server != null;

        Text msg = Text.literal("¡Has sido PERMABANEADO!").setStyle(Style.EMPTY.withColor(Formatting.RED));

        GameProfile playerProfile = player.getGameProfile(); // Get the player's game profile
        PlayerManager playerManager = server.getPlayerManager(); // Get the player manager

        playerManager.getUserBanList().add(new BannedPlayerEntry(playerProfile, null, null, null, msg.getString())); // Ban the player
        player.networkHandler.disconnect(msg); // Disconnect the player
    }

    public static void register(ServerPlayerEntity player) {
        makePillar(player);
        announceDeath(player);
        DeathTrain.startDeathTrain();
        if (ModConfig.getBanOnDeath()) banOnDeath(player);
    }
}
