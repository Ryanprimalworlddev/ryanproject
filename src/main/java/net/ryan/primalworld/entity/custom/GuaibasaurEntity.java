package net.ryan.primalworld.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.ryan.primalworld.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Represents the GuaibasaurEntity, a custom animal entity with unique behaviors and features. 
 * This class extends the base {@link Animal} class and introduces specific attributes and 
 * actions related to the Guaibasaur creature.
 */
public class GuaibasaurEntity extends Animal {

    // Variável para armazenar o gênero do mob
    private boolean isMale;

    public GuaibasaurEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        //MACHO GENERO
        this.isMale = new Random().nextBoolean();
    }

    public boolean isMale() {
        return this.isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isMale", this.isMale);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("isMale")) {
            this.isMale = compound.getBoolean("isMale"); // Carrega o gênero do NBT
        }

        //MACHO GENERO
    }

    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState wakingUpAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private LivingEntity attacker; // Tracks the entity that attacked this mob
    private int fleeingTimer = 0;  // Timer to manage fleeing duration


    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationsStates();
        }

        // Handle fleeing logic
        if (fleeingTimer > 0) {
            --fleeingTimer;

            if (attacker != null) {
                double fleeX = this.getX() + (this.getX() - attacker.getX());
                double fleeZ = this.getZ() + (this.getZ() - attacker.getZ());
                this.navigation.moveTo(fleeX, this.getY(), fleeZ, 1.5); // Move away from attacker
            }

            setSpeed(1.5F); // Sets the fleeing speed modifier

            if (level().isClientSide() && !runAnimationState.isStarted()) {
                runAnimationState.start(this.tickCount); // Ensure RUN animation is played
            }

        } else if (attacker != null) { // Reset logic when fleeing ends
            attacker = null;
            setSpeed(0.25F); // Revert to normal speed
            if (level().isClientSide() && !idleAnimationState.isStarted()) {
                idleAnimationState.start(this.tickCount); // Switch to IDLE animation
            }
        }

        // Stop the RUN animation when the flee timer ends
        if (fleeingTimer == 0 && runAnimationState.isStarted()) {
            runAnimationState.stop();
        }
    }

    private void setupAnimationsStates() {
        if (fleeingTimer == 0) { // Prevents idle animation conflicts during fleeing
            if (this.idleAnimationTimeout <= 0 && !idleAnimationState.isStarted()) {
                this.idleAnimationTimeout = 160;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING && fleeingTimer == 0) {
            f = Math.min(pPartialTick * 6F, 1f); // Smooth walking while idle
        } else if (fleeingTimer > 0) {
            f = 1f; // Full intensity during fleeing
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 0.5D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.2D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    private void startFleeing(LivingEntity entity) {
        this.attacker = entity;
        this.fleeingTimer = 300; // Flee for 15 seconds (300 ticks)

        double fleeX = this.getX() + (this.getX() - entity.getX());
        double fleeZ = this.getZ() + (this.getZ() - entity.getZ());
        this.navigation.moveTo(fleeX, this.getY(), fleeZ, 1.5); // Move away from attacker

        if (level().isClientSide()) {
            if (!runAnimationState.isStarted()) {
                runAnimationState.start(this.tickCount); // Start RUN animation
            }
            if (idleAnimationState.isStarted()) {
                idleAnimationState.stop(); // Ensure IDLE animation is reset during fleeing
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 8f)
                .add(Attributes.ATTACK_KNOCKBACK, 5f)
                .add(Attributes.ATTACK_DAMAGE, 5f);
    }


    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.GUAIBASAUR.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.WHEAT);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        if (pDamageSource.getEntity() instanceof LivingEntity livingEntity) {
            startFleeing(livingEntity); // Trigger fleeing when hurt
        }
        return super.getHurtSound(pDamageSource);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    protected SoundEvent getDrinkingSound(ItemStack pStack) {
        return super.getDrinkingSound(pStack);
    }

    @Override
    protected SoundEvent getSwimSplashSound() {
        return super.getSwimSplashSound();
    }

}