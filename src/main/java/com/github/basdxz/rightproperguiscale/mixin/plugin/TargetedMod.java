package com.github.basdxz.rightproperguiscale.mixin.plugin;

public enum TargetedMod {
    BETTER_LOADING_SCREEN("Better Loading Screen", null, "betterloadingscreen"),
    BETTER_QUESTING("BetterQuesting", null, "betterquesting"),
    OPTIFINE("Optifine", "optifine.OptiFineForgeTweaker", "Optifine"),
    VANILLA("Minecraft", null, null);

    /** The "name" in the {@link cpw.mods.fml.common.Mod @Mod} annotation */
    public final String modName;
    /** Class that implements the IFMLLoadingPlugin interface */
    public final String coreModClass;
    /** The "modid" in the {@link cpw.mods.fml.common.Mod @Mod} annotation */
    public final String modId;

    TargetedMod(String modName, String coreModClass, String modId) {
        this.modName = modName;
        this.coreModClass = coreModClass;
        this.modId = modId;
    }

    @Override
    public String toString() {
        return "TargetedMod{modName='" + modName + "', coreModClass='" + coreModClass + "', modId='" + modId + "'}";
    }
}
