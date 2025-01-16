package com.beansgalaxy.template.neoforge;

import com.beansgalaxy.template.Main;
import net.neoforged.fml.common.Mod;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(Main.MOD_ID)
public class MainForge {
    public MainForge() {
        Main.init();
    }
}
