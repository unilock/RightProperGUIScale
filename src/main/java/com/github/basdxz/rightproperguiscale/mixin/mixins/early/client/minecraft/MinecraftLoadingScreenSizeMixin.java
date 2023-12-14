package com.github.basdxz.rightproperguiscale.mixin.mixins.early.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nonnull;

import static com.github.basdxz.rightproperguiscale.util.Util.framebufferRender;

/**
 * Mixin to fix the early {@link Minecraft} loading screen to use proper display width and height for it's
 * {@link Framebuffer}. Preventing wierd bars on the right/top, from the screen not being fully filled.
 * <p>
 * This patch is incompatible with {@link TargetedMod#BETTER_LOADING_SCREEN} since this mod removes the targeted
 * code. However, this mod also doesn't experience the bug this patch intended to fix.
 */
@Unique
@Mixin(Minecraft.class)
public abstract class MinecraftLoadingScreenSizeMixin {
    @Shadow
    public int displayWidth;
    @Shadow
    public int displayHeight;

    /**
     * Injects right after the frame buffer has been creates and resets it to be the right size.
     *
     * @param instance    framebuffer
     * @param setViewport viewport state
     */
    @Redirect(method = "loadScreen()V",
              at = @At(value = "INVOKE",
                       target = "net/minecraft/client/shader/Framebuffer.bindFramebuffer (Z)V",
                       ordinal = 0),
              require = 1)
    private void fixNewFramebuffer(Framebuffer instance, boolean setViewport) {
        resetFramebuffer(instance, setViewport);
    }

    /**
     * Resets the frame buffer to the current screen size.
     *
     * @param framebuffer framebuffer
     * @param setViewport viewport state
     */
    private void resetFramebuffer(@Nonnull Framebuffer framebuffer, boolean setViewport) {
        framebuffer.createBindFramebuffer(displayWidth, displayHeight);
        framebuffer.bindFramebuffer(setViewport);
    }

    /**
     * Injects before the {@link Framebuffer} has been rendered out to screen and renders the right size.
     *
     * @param instance framebuffer
     * @param width    screen width
     * @param height   screen height
     */
    @Redirect(method = "loadScreen()V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V",
                       ordinal = 0),
              require = 1)
    private void fixFramebufferRender(Framebuffer instance, int width, int height) {
        framebufferRender(instance);
    }
}
