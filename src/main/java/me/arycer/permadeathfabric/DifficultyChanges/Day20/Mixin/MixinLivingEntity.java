package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.PassiveToAggressive;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D", at = @At("HEAD"), cancellable = true)
    private void getAttributeValue(EntityAttribute attribute, CallbackInfoReturnable<Double> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        EntityType<?> type = entity.getType();
        if (attribute == EntityAttributes.GENERIC_ATTACK_DAMAGE && PassiveToAggressive.list.contains(type)) {
            cir.setReturnValue(2.0D);
        }
    }
}