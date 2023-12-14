package com.github.basdxz.rightproperguiscale.mixin.mixins.late.client.betterquesting;

import betterquesting.api.utils.RenderUtils;
import betterquesting.api2.client.gui.misc.GuiRectangle;
import betterquesting.api2.client.gui.misc.IGuiRect;
import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.util.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.nio.FloatBuffer;

/**
 * A Mixin for {@link RenderUtils} to fix the offset and scale in {@link RenderUtils#startScissor}.
 * <p>
 * The warnings are supressed as the Minecraft Development plugin for Intellij IDEA fails to recongise the entry points.
 */
@SuppressWarnings("ALL")
@Mixin(value = RenderUtils.class, remap = false)
public abstract class RenderUtilsScissorAlignmentMixin {
    /**
     * A cached rectangle, created when the local variables are captured and used when the rectangle would be created.
     */
    private static final ThreadLocal<GuiRectangle> cachedRectangle = new ThreadLocal<>();

    /**
     * Injects right before the scissor is started, captures the variables in scope.
     * Then creates and caches a new {@link GuiRectangle} with the correct scale and offset.
     *
     * @param rect rectangle to scissor
     * @param ci   mixin callback info
     * @param mc   minecraft instance
     * @param r    scaled resolution
     * @param f    original gui scale
     * @param fb   model view float buffer
     * @param fm   model view matrix
     */
    @Inject(method = "startScissor(Lbetterquesting/api2/client/gui/misc/IGuiRect;)V",
            at = @At(value = "NEW",
                     target = "(IIII)Lbetterquesting/api2/client/gui/misc/GuiRectangle;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private static void createScissorRectangle(IGuiRect rect,
                                               CallbackInfo ci,
                                               Minecraft mc,
                                               ScaledResolution r,
                                               int f,
                                               FloatBuffer fb,
                                               Matrix4f fm) {
        var scaledResolution = (IScaledResolutionMixin) r;
        var scale = scaledResolution.scaleFactor();
        var scaledHeight = (float) scaledResolution.scaledHeight();

        // Does not change the behavior of the original code, simply makes it scale with the float scale factor.
        var posX = safeRound((rect.getX() * scale * fm.m00) + (fm.m30 * scale));
        var posY = safeRound((scaledHeight - ((rect.getY() + rect.getHeight()) * fm.m11 + fm.m31)) * scale);
        var width = safeRound(rect.getWidth() * scale * fm.m00);
        var height = safeRound(rect.getHeight() * scale * fm.m11);

        cachedRectangle.set(new GuiRectangle(posX, posY, width, height));
    }

    /**
     * Rounds a float to the nearest integer, clamping it to be more than or equal to one.
     * <p>
     * Important as otherwise GUI elements may have a scale of zero, which causes problems.
     *
     * @param value the value to round
     * @return the rounded value clamped to >= 1
     */
    private static int safeRound(float value) {
        return Math.max(Math.round(value), 1);
    }

    /**
     * Injects into the construction of the {@link GuiRectangle}, replacing it with the cached value if one was created.
     *
     * @param x the original x coordinate of the rectangle
     * @param y the original y coordinate of the rectangle
     * @param w the original width of the rectangle
     * @param h the original height of the rectangle
     * @return the cached rectangle with the correct offset and scale, or a new rectangle if none was cached
     */
    @Redirect(method = "startScissor(Lbetterquesting/api2/client/gui/misc/IGuiRect;)V",
              at = @At(value = "NEW",
                       target = "(IIII)Lbetterquesting/api2/client/gui/misc/GuiRectangle;"),
              require = 1)
    private static GuiRectangle replaceScissorRectangle(int x, int y, int w, int h) {
        var rect = cachedRectangle.get();
        if (rect == null)
            new GuiRectangle(x, y, w, h);
        cachedRectangle.remove();
        return rect;
    }
}
