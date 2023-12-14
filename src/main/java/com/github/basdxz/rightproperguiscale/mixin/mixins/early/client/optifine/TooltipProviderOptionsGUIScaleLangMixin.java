package com.github.basdxz.rightproperguiscale.mixin.mixins.early.client.optifine;


import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.basdxz.rightproperguiscale.util.Util.isGUIScaleOption;

/**
 * OptiFine specific Mixin to replace the Video Settings tooltip.
 */
@Pseudo
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "TooltipProviderOptions", remap = false)
public abstract class TooltipProviderOptionsGUIScaleLangMixin {
    private static final String GUI_SCALE_UNLOCALIZED_TAG = "options.rightProperGuiScale";

    /**
     * Injects blindly into when OptiFine is loading the lang for the Settings.
     * Redirects any invocation that would request the enum name of the {@link GameSettings.Options#GUI_SCALE} option,
     * replacing it with {@link TooltipProviderOptionsGUIScaleLangMixin#GUI_SCALE_UNLOCALIZED_TAG} or returning the original value.
     *
     * @param option Setting option
     * @return enum string
     */
    @Redirect(method = "getTooltipLines(Lnet/minecraft/client/gui/GuiButton;I)[Ljava/lang/String;",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/settings/GameSettings$Options;func_74378_d()" +
                                "Ljava/lang/String;"),
              require = 1)
    private String replaceGUIScaleEnumString(GameSettings.Options option) {
        if (isGUIScaleOption(option))
            return GUI_SCALE_UNLOCALIZED_TAG;
        return option.getEnumString();
    }
}
