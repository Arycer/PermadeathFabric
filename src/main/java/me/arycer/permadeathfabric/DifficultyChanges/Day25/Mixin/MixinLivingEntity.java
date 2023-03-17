package me.arycer.permadeathfabric.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day25.ModifyLootTables;
import me.arycer.permadeathfabric.Util.ModConfig;
import me.arycer.permadeathfabric.Util.WorldUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(method = "tick", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;
        ServerWorldProperties properties = WorldUtils.getProperties();
        if (!properties.isThundering()) return;
        LivingEntity mob = (LivingEntity) (Object) this;
        if (mob.getType().equals(EntityType.PLAYER)) return;

        StatusEffectInstance resistance = new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 1);
        StatusEffectInstance strength = new StatusEffectInstance(StatusEffects.STRENGTH, 10, 1);
        StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 10, 1);

        mob.addStatusEffect(resistance);
        mob.addStatusEffect(strength);
        mob.addStatusEffect(speed);
    }

    @Inject(method = "getLootTable", at = @At("RETURN"), cancellable = true)
    private void register(CallbackInfoReturnable<Identifier> cir) {
        int day = ModConfig.getServerDay();
        EntityType<?> type = ((LivingEntity) (Object) this).getType();
        if (day < 25 || !ModifyLootTables.ModifiedLootTable.contains(type)) return;
        ModifyLootTables.register(cir, (LivingEntity) (Object) this);
    }
}