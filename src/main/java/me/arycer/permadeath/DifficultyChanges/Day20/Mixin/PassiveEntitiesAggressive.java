package me.arycer.permadeath.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin({AllayEntity.class, AnimalEntity.class, DolphinEntity.class, FishEntity.class, ParrotEntity.class, SnowGolemEntity.class, SquidEntity.class, VillagerEntity.class, WanderingTraderEntity.class, TameableEntity.class})
public abstract class PassiveEntitiesAggressive extends PathAwareEntity {
    protected PassiveEntitiesAggressive(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private boolean isAggressive = false;

    @Override
    public void tick() {
        super.tick();

        int day = ModConfig.getServerDay();
        if (day < 20) {
            if (isAggressive) {
                isAggressive = false;
                this.goalSelector.getGoals().forEach(this.goalSelector::remove);
                this.targetSelector.getGoals().forEach(this.targetSelector::remove);
                this.setTarget(null);
                super.initGoals();
            }
            return;
        }

        if (this.getType().equals(EntityType.CAT)) {
            this.goalSelector.getGoals().removeIf((it) -> it.getGoal() instanceof FleeEntityGoal<?>);
        }

        if (isAggressive) return;

        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(0, new ActiveTargetGoal(this, PlayerEntity.class, true, true));
        this.targetSelector.add(0, new UniversalAngerGoal(this, true));

        AttributeContainer container = this.getAttributes();
        ((AttributeContainerAccessor) container).getCustom().putIfAbsent(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE, (it) -> {}));
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(8.0D);
    }
}