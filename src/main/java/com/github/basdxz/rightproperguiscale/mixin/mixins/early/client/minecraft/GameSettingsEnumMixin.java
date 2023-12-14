package com.github.basdxz.rightproperguiscale.mixin.mixins.early.client.minecraft;

import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import net.minecraft.client.settings.GameSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameSettings.Options.class)
public class GameSettingsEnumMixin {
    @Shadow
    @Final
    private String enumString;

    @Shadow
    @Final
    private float valueStep;

    @Shadow
    float valueMin;

    @Shadow
    float valueMax;

    @Inject(method = "Lnet/minecraft/client/settings/GameSettings$Options;<init>(Ljava/lang/String;ZZ)V", at = @At("RETURN"))
    private void init(String type, int ordinal, String enumString, boolean enumFloat, boolean enumBoolean, CallbackInfo ci) {
        if ("options.guiScale".equals(enumString)) {
            this.valueMin = RightProperGUIScaleConfig.GUI_SCALE_MIN;
            this.valueMax = RightProperGUIScaleConfig.GUI_SCALE_MAX;
        }
    }

    @Inject(method = "Lnet/minecraft/client/settings/GameSettings$Options;getEnumFloat()Z", at = @At("RETURN"), cancellable = true)
    private void getEnumFloat(CallbackInfoReturnable<Boolean> cir) {
        if ("options.guiScale".equals(this.enumString)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "Lnet/minecraft/client/settings/GameSettings$Options;snapToStep(F)F", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings$Options;valueStep:F", opcode = Opcodes.GETFIELD))
    private float getValueStep(GameSettings.Options options) {
        if ("options.guiScale".equals(this.enumString)) {
            return RightProperGUIScaleConfig.GUI_SCALE_STEP;
        } else {
            return this.valueStep;
        }
    }
}
