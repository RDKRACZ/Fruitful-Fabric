package com.teamaurora.fruitful.core;

import co.eltrut.differentiate.core.registrator.Registrator;
import com.google.common.reflect.Reflection;
import com.teamaurora.fruitful.core.config.FruitfulConfig;
import com.teamaurora.fruitful.core.other.FruitfulCompat;
import com.teamaurora.fruitful.core.other.FruitfulEvents;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulEffects;
import com.teamaurora.fruitful.core.registry.FruitfulFeatures;
import com.teamaurora.fruitful.core.registry.FruitfulItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Fruitful implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_NAME = "Fruitful";
    public static final String MOD_ID = "fruitful";

    public static final Registrator REGISTRATOR = new Registrator(MOD_ID);

    @Override
    public void onInitialize() {
        FruitfulConfig.registerConfig();

        // Configured Features //
        FruitfulFeatures.Configured.register("flowering_oak", FruitfulFeatures.Configured.FLOWERING_OAK);
        FruitfulFeatures.Configured.register("flowering_fancy_oak", FruitfulFeatures.Configured.FLOWERING_FANCY_OAK);
        FruitfulFeatures.Configured.register("flowering_oak_bees_005", FruitfulFeatures.Configured.FLOWERING_OAK_BEES_005);
        FruitfulFeatures.Configured.register("flowering_fancy_oak_bees_005", FruitfulFeatures.Configured.FLOWERING_FANCY_OAK_BEES_005);
        FruitfulFeatures.Configured.register("flowering_oak_bees_002", FruitfulFeatures.Configured.FLOWERING_OAK_BEES_002);
        FruitfulFeatures.Configured.register("flowering_fancy_oak_bees_002", FruitfulFeatures.Configured.FLOWERING_FANCY_OAK_BEES_002);

        FruitfulFeatures.Configured.register("flowering_oak_infrequent", FruitfulFeatures.Configured.FLOWERING_OAK_INFREQUENT);
        FruitfulFeatures.Configured.register("forest_flower_trees", FruitfulFeatures.Configured.FOREST_FLOWER_TREES);

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

        Reflection.initialize(
                FruitfulEffects.class,
                FruitfulCompat.class,
                FruitfulBlocks.class,
                FruitfulItems.class,
                FruitfulEvents.class,
                FruitfulFeatures.class
        );

        log(Level.INFO, "Fruity!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}