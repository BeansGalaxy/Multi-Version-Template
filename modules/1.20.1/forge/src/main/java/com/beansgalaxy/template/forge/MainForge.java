package com.beansgalaxy.template.forge;

import com.beansgalaxy.template.Main;
import net.minecraftforge.fml.common.Mod;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(Main.MOD_ID)
public class MainForge {
    public MainForge() {
        Main.init();
    }
}
