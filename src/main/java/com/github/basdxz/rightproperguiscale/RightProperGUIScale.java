package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.mixin.plugin.Mixins;
import com.github.basdxz.rightproperguiscale.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Forge entry point, loaded after {@link Mixins} has been processed.
 */
@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class RightProperGUIScale {
    /**
     * Logger provided by Forge.
     */
    public static final Logger logger = LogManager.getLogger(Tags.MODID);
    /**
     * Proxy injected by Forge.
     */
    @SidedProxy(clientSide = "com.github.basdxz.rightproperguiscale.proxy.ClientProxy", serverSide = "com.github.basdxz.rightproperguiscale.proxy.ServerProxy")
    public static CommonProxy proxy;

    /**
     * Executed shortly after the mod is loaded.
     * <p>
     * Redirects to the forge provided proxy.
     *
     * @param event Forge pre init event
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}
