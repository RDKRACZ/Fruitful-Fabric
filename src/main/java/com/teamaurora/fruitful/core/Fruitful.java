package com.teamaurora.fruitful.core;

import com.teamaurora.fruitful.core.config.FruitfulConfig;
import com.teamaurora.fruitful.core.other.FruitfulCompat;
import com.teamaurora.fruitful.core.other.FruitfulEvents;
import com.teamaurora.fruitful.core.registry.FruitfulFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Fruitful implements ModInitializer {
    public static final String MODID = "fruitful";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        FruitfulConfig.registerConfig();

        FruitfulFeatures.registerFeatures();
        FruitfulEvents.registerEvents();
        FruitfulCompat.registerCompact();

        LOGGER.log(Level.INFO, "Fruity!");
    }

    public static Identifier id(String path) {
        return new Identifier(Fruitful.MODID, path);
    }
}