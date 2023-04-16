package me.arycer.permadeath.DifficultyChanges.Global.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class MixinSpiderEntity {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        SpiderEntity spider = (SpiderEntity) (Object) this;
        if (!spider.hasPassengers() || spider.getPassengerList().size() == 1) return;

        int passengerCount = spider.getPassengerList().size();
        while (passengerCount > 1) {
            spider.getPassengerList().get(1).remove(Entity.RemovalReason.DISCARDED);
            passengerCount--;
        }
    }
}
