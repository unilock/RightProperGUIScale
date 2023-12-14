package com.github.basdxz.rightproperguiscale.mixin.mixins.early.client.minecraft.accessor;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameSettings.Options.class)
public interface GameSettingsOptionsAccessor {
    @Accessor
    void setValueMin(float value);
}
