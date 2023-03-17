package me.arycer.permadeathfabric.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeathfabric.Util.EntityUtils;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        int netheritePieces = 0;
        if (helmet.getItem().equals(Items.NETHERITE_HELMET)) netheritePieces++;
        if (chestplate.getItem().equals(Items.NETHERITE_CHESTPLATE)) netheritePieces++;
        if (leggings.getItem().equals(Items.NETHERITE_LEGGINGS)) netheritePieces++;
        if (boots.getItem().equals(Items.NETHERITE_BOOTS)) netheritePieces++;

        EntityUtils.setMaxHealth(player, netheritePieces == 4 ? 28 : 20, false);
        if (netheritePieces < 4 && player.getHealth() > 20) player.setHealth(20);
    }
}
