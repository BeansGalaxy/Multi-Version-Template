package com.beansgalaxy.template.fabric;

import com.beansgalaxy.template.Main;
import net.fabricmc.api.ModInitializer;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 */
public class MainFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Main.init();
    }
}
