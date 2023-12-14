package com.github.basdxz.rightproperguiscale.mixin.mixins.early.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A Mixin for {@link GameSettings} that appends the GUI Scale with {@link GUIScale#vanillaValue()}.
 */
@Mixin(GameSettings.class)
public abstract class GameSettingsInitGUIScaleMixin {
    @Shadow
    public int guiScale;

    /**
     * Injects at the end of the constructor and initialised the GUI Scale value.
     *
     * @param ci mixin callback info
     */
    @Inject(method = {"<init>()V",
                      "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V"},
            at = @At(value = "RETURN"),
            require = 1)
    private void appendConstructor(CallbackInfo ci) {
        initGUIScale();
    }

    /**
     * Initializes the {@link GameSettings#guiScale} value to {@link GUIScale#vanillaValue()}.
     */
    private void initGUIScale() {
        guiScale = GUIScale.vanillaValue();
    }
}
