package net.ryan.primalworld.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.ryan.primalworld.entity.CarnivoroMedio;
import net.ryan.primalworld.entity.ModEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;

public class IrritatorEntity extends Animal implements GeoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isSwinging = false;
    private AnimationController<IrritatorEntity> attackController;
    private Genero genero;
    private CarnivoroMedio needs;

    public enum Genero {
        MACHO, FEMEA
    }

    public IrritatorEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        Random random = new Random();
        this.genero = random.nextBoolean() ? Genero.MACHO : Genero.FEMEA;
        this.needs = new CarnivoroMedio();
    }

    public Genero getGenero() {
        return genero;
    }

    @Override
    public void tick() {
        super.tick();
        needs.updateNeeds();

        if (needs.isHungry()) {
            needs.findFood();
        }
        if (needs.isThirsty()) {
            needs.findWater();
        }
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 8f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ATTACK_DAMAGE, 5f);
    }

    public boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.PORKCHOP), false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.5D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Sheep.class, true));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Cow.class, true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "movementController", 0, this::movementPredicate));
        this.attackController = new AnimationController<>(this, "attackController", 0, this::attackPredicate);
        controllerRegistrar.add(this.attackController);
    }

    private <E extends GeoEntity> PlayState movementPredicate(AnimationState<E> event) {
        if (this.getTarget() != null && this.distanceTo(this.getTarget()) > 2.0) {
            event.getController().setAnimation(RawAnimation.begin().then("irritator.chase", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("irritator.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().then("irritator.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends GeoEntity> PlayState attackPredicate(AnimationState<E> event) {
        if (this.swinging) {
            this.isSwinging = true;
            event.getController().setAnimation(RawAnimation.begin().then("irritator.attack", Animation.LoopType.PLAY_ONCE));

            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.isSwinging = false;
                this.swinging = false;
                if (this.attackController != null) {
                    this.attackController.forceAnimationReset();
                }
            }
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        IrritatorEntity baby = ModEntities.IRRITATOR.get().create(serverLevel);
        if (baby != null) {
            // Define o tempo que o bebê levará para crescer. Exemplo: 24000 ticks = 20 minutos
            baby.setAge(-1200); // -24000 ticks significa que ele começa como bebê e levará 20 minutos para crescer
        }
        return baby;
    }


    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.PORKCHOP);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}