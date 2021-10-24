package com.teamaurora.fruitful.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.fruitful.core.Fruitful;
import com.teamaurora.fruitful.core.config.FruitfulConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.apache.logging.log4j.Level;

import java.util.Objects;
import java.util.OptionalInt;

public class FruitfulFeatures {
    static {
        // Configured Features //
        Configured.register("flowering_oak", Configured.FLOWERING_OAK);
        Configured.register("flowering_fancy_oak", Configured.FLOWERING_FANCY_OAK);
        Configured.register("flowering_oak_bees_005", Configured.FLOWERING_OAK_BEES_005);
        Configured.register("flowering_fancy_oak_bees_005", Configured.FLOWERING_FANCY_OAK_BEES_005);
        Configured.register("flowering_oak_bees_002", Configured.FLOWERING_OAK_BEES_002);
        Configured.register("flowering_fancy_oak_bees_002", Configured.FLOWERING_FANCY_OAK_BEES_002);

        Configured.register("flowering_oak_infrequent", Configured.FLOWERING_OAK_INFREQUENT);
        Configured.register("forest_flower_trees", Configured.FOREST_FLOWER_TREES);

        // Modifications //
        var flowerBiomes = FruitfulConfig.get().flowerBiomes.toString().replace("[", "").replace("]", "").replace(" ", "").split(",", FruitfulConfig.get().flowerBiomes.size());
        for (int flowerBiome = 0; flowerBiome < flowerBiomes.length; flowerBiome++) {
            var biomeSelector = BiomeSelectors.includeByKey(RegistryKey.of(Registry.BIOME_KEY, new Identifier(flowerBiomes[flowerBiome])));
            BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.VEGETAL_DECORATION, Objects.requireNonNull(FruitfulFeatures.registryKey(FruitfulFeatures.Configured.FLOWERING_OAK_INFREQUENT)));
        }

        if (FruitfulConfig.get().flowerBiomes.toString().contains(BiomeKeys.FLOWER_FOREST.getValue().toString())){
            BiomeModifications.create(Fruitful.id("remove_flower_forest_trees"))
                    .add(ModificationPhase.REPLACEMENTS, BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), (c)-> {
                        if(c.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FOREST_FLOWER_TREES))
                        {
                            c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, FruitfulFeatures.registryKey(FruitfulFeatures.Configured.FOREST_FLOWER_TREES));
                        }
                    });
        }
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(ConfiguredFeature<?, ?> carver)
    {
        if (BuiltinRegistries.CONFIGURED_FEATURE.getKey(carver).isPresent()) {
            return BuiltinRegistries.CONFIGURED_FEATURE.getKey(carver).get();
        } else {
            Fruitful.LOGGER.log(Level.INFO, "Something Went Wrong Registering Carver!");
            return null;
        }
    }

    public static final class BlockStates {
        public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        public static final BlockState FLOWERING_OAK_LEAVES = FruitfulBlocks.FLOWERING_OAK_LEAVES.getDefaultState();
        public static final BlockState BUDDING_OAK_LEAVES = FruitfulBlocks.BUDDING_OAK_LEAVES.getDefaultState();
    }

    static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }

    public static final class Configs {
        public static final TreeFeatureConfig FLOWERING_OAK = (new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.OAK_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                new WeightedBlockStateProvider(pool().add(BlockStates.BUDDING_OAK_LEAVES, 2).add(BlockStates.FLOWERING_OAK_LEAVES, 1)),
                new SimpleBlockStateProvider(FruitfulBlocks.FLOWERING_OAK_SAPLING.getDefaultState()),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
        ).ignoreVines().build();
        public static final TreeFeatureConfig FLOWERING_FANCY_OAK = (new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.OAK_LOG),
                new LargeOakTrunkPlacer(3, 11, 0),
                new WeightedBlockStateProvider(pool().add(BlockStates.BUDDING_OAK_LEAVES, 2).add(BlockStates.FLOWERING_OAK_LEAVES, 1)),
                new SimpleBlockStateProvider(FruitfulBlocks.FLOWERING_OAK_SAPLING.getDefaultState()),
                new LargeOakFoliagePlacer(ConstantIntProvider.create(2),
                ConstantIntProvider.create(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
        ).ignoreVines().build();
    }

    public static final class Configured {
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_OAK = Feature.TREE.configure(Configs.FLOWERING_OAK);
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_FANCY_OAK = Feature.TREE.configure(Configs.FLOWERING_FANCY_OAK);
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_OAK_BEES_005 = Feature.TREE.configure(Configs.FLOWERING_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_FANCY_OAK_BEES_005 = Feature.TREE.configure(Configs.FLOWERING_FANCY_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_OAK_BEES_002 = Feature.TREE.configure(Configs.FLOWERING_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.REGULAR_BEEHIVES_TREES)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_FANCY_OAK_BEES_002 = Feature.TREE.configure(Configs.FLOWERING_FANCY_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.REGULAR_BEEHIVES_TREES)));

        public static final ConfiguredFeature<?, ?> FLOWERING_OAK_INFREQUENT = FLOWERING_OAK.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.12F, 3)));
        public static final ConfiguredFeature<?, ?> FOREST_FLOWER_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(ConfiguredFeatures.BIRCH_BEES_002.withChance(0.2F), FLOWERING_FANCY_OAK_BEES_002.withChance(0.05F), ConfiguredFeatures.FANCY_OAK_BEES_002.withChance(0.053F), ConfiguredFeatures.OAK_BEES_002.withChance(0.5F)), FLOWERING_OAK_BEES_002)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.1F, 1)));

        public static <FC extends FeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Fruitful.id(name), configuredFeature);
        }
    }
}