package me.arycer.permadeath.DifficultyChanges.Global;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import static me.arycer.permadeath.Util.ServerUtil.createText;

public class DeathHandler {
    private static void deathPillar(ServerPlayerEntity player) {
        World world = player.getWorld();

        world.setBlockState(player.getBlockPos().up(), Blocks.PLAYER_HEAD.getDefaultState());
        world.setBlockState(player.getBlockPos(), Blocks.NETHER_BRICK_FENCE.getDefaultState());
        world.setBlockState(player.getBlockPos().down(), Blocks.BEDROCK.getDefaultState());

        SkullBlockEntity skull = (SkullBlockEntity) world.getBlockEntity(player.getBlockPos().up());
        assert skull != null;

        skull.setOwner(player.getGameProfile());
        skull.markDirty();
    }

    private static void announceDeath(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        assert server != null;

        String playerName = player.getName().getString();
        int x = player.getBlockPos().getX();
        int y = player.getBlockPos().getY();
        int z = player.getBlockPos().getZ();

        Text chat1_1 = createText("El comienzo del sufrimiento infinito de ", Formatting.RED, true);
        Text chat1_2 = createText(playerName, Formatting.DARK_RED, true);
        Text chat1_3 = createText(" ha comenzado. ¡HA SIDO PERMABANEADO!", Formatting.RED, true);

        Text chat1 = chat1_1.copy().append(chat1_2).append(chat1_3);
        server.getPlayerManager().broadcast(chat1, false);

        Text chat2 = createText(String.format("El jugador %s ha muerto en X: %d, Y: %d, Z: %d", playerName, x, y, z), Formatting.GRAY, false);
        server.getPlayerManager().broadcast(chat2, false);

        Text title = createText("¡Permadeath!", Formatting.RED, false);
        Text subtitle = createText(String.format("%s ha muerto.", playerName), Formatting.WHITE, false);

        server.getPlayerManager().getPlayerList().forEach(p -> {
            p.playSound(SoundEvents.ENTITY_BLAZE_DEATH, SoundCategory.AMBIENT, Float.MAX_VALUE, -0.1f);
            p.networkHandler.sendPacket(new TitleS2CPacket(title));
            p.networkHandler.sendPacket(new SubtitleS2CPacket(subtitle));
        });
    }

    private static void banPlayer(ServerPlayerEntity player) {
        if (!ModConfig.getBanOnDeath()) return;

        MinecraftServer server = player.getServer();
        assert server != null;

        if (server.getPlayerManager().isOperator(player.getGameProfile()) && ModConfig.getOpBanImmunity()) return;

        Text msg = createText("¡Has sido PERMABANEADO!", Formatting.RED, false);

        server.getPlayerManager().getUserBanList().add(new BannedPlayerEntry(player.getGameProfile(), null, null, null, msg.getString()));
        player.networkHandler.disconnect(msg);
    }

    public static void onDeath(ServerPlayerEntity player) {
        announceDeath(player);
        deathPillar(player);
        banPlayer(player);
        DeathTrain.startDeathTrain();
    }
}