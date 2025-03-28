package net.ryan.primalworld.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.primalworld;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, primalworld.MOD_ID );

    public static final RegistryObject<RecipeSerializer<FossilCleaningRecipe>> FOSSIL_CLEANING_SEARIALIZER =
            SERIALIZERS.register("fossil_cleaning", () -> FossilCleaningRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}