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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.basdxz.rightproperguiscale.util.Util.isGUIScaleOption;

@Mixin(GameSettings.Options.class)
public class GameSettingsOptionsMixin {
    @Shadow
    @Final
    private float valueStep;

    @Inject(method = "getEnumFloat()Z", at = @At("RETURN"), cancellable = true)
    private void getEnumFloat(CallbackInfoReturnable<Boolean> cir) {
        if (isGUIScaleOption((GameSettings.Options) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "snapToStep(F)F", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings$Options;valueStep:F", opcode = Opcodes.GETFIELD))
    private float getValueStep(GameSettings.Options options) {
        if (isGUIScaleOption((GameSettings.Options) (Object) this)) {
            return RightProperGUIScaleConfig.GUI_SCALE_STEP;
        } else {
            return this.valueStep;
        }
    }
}
