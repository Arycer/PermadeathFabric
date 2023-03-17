package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.Main;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Objects;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void tryUseTotem(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (ModConfig.getServerDay() < 30 || !entity.getType().equals(EntityType.PLAYER)) return;

        boolean isTotemInHand = entity.getStackInHand(Hand.MAIN_HAND).isOf(Items.TOTEM_OF_UNDYING) || entity.getStackInHand(Hand.OFF_HAND).isOf(Items.TOTEM_OF_UNDYING);
        if (!isTotemInHand) return;

        int random = (int) (Math.random() * 100) + 1;
        if (random == 100) {
            Text msg = Text.literal("¡" + entity.getName().getString() + " ha usado un Tótem de la Inmortalidad, pero ha fallado!")
                    .setStyle(Style.EMPTY.withColor(Formatting.GRAY));
            Objects.requireNonNull(entity.getServer()).getPlayerManager().broadcast(msg, false);
            cir.setReturnValue(false);
        } else {
            Text msg = Text.literal("¡" + entity.getName().getString() + " ha usado un Tótem de la Inmortalidad!")
                    .setStyle(Style.EMPTY.withColor(Formatting.GRAY));
            Objects.requireNonNull(entity.getServer()).getPlayerManager().broadcast(msg, false);
        }

        Main.LOGGER.info(entity.getName().getString() + " ha usado un tótem, random: " + random + "/100");
    }
}