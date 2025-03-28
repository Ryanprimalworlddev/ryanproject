package net.ryan.primalworld.world;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.ryan.primalworld.primalworld;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> AMBER_ORE_PLACED_KEY = registerKey("amber_ore_placed");
    public static final ResourceKey<PlacedFeature> FOSSIL_PLACED_KEY = registerKey("fossil_placed");
    public static final ResourceKey<PlacedFeature> DIFFERENT_FOSSIL_PLACED_KEY = registerKey("different_fossil_placed");
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE_PLACED_KEY = registerKey("titanium_ore_placed");
    public static final ResourceKey<PlacedFeature> BROWN_SEAGRASS_PLACED_KEY = registerKey("brown_seagrass_placed"); // Mudado para BROWN_SEAGRASS

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.AMBER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FOSSIL_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, DIFFERENT_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DIFFERENT_FOSSIL_KEY),
                ModOrePlacement.commonOrePlacement(48,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, TITANIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TITANIUM_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

        register(context, BROWN_SEAGRASS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BROWN_SEAGRASS_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(primalworld.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
