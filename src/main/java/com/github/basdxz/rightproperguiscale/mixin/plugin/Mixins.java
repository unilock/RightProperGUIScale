package com.github.basdxz.rightproperguiscale.mixin.plugin;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public enum Mixins {
    GameSettingsOptionsAccessor(new Builder("GameSettingsOptionsAccessor")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.accessor.GameSettingsOptionsAccessor")),

    GameSettingsOptions(new Builder("GameSettingsOptions")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GameSettingsOptionsMixin")),
    GameSettingsInitGUIScale(new Builder("GameSettingsInitGUIScale")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GameSettingsInitGUIScaleMixin")),
    GameSettingsGUIScaleSliderMixin(new Builder("GameSettingsGUIScaleSliderMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GameSettingsGUIScaleSliderMixin")),
    GameSettingsGUIScaleLoadSaveMixin(new Builder("GameSettingsGUIScaleLoadSaveMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GameSettingsGUIScaleLoadSaveMixin")),
    ScaledResolutionPatchMixin(new Builder("ScaledResolutionPatchMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.ScaledResolutionPatchMixin")),
    GuiEnchantmentBookAlignmentMixin(new Builder("GuiEnchantmentBookAlignmentMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GuiEnchantmentBookAlignmentMixin")),
    LoadingScreenRendererSizeMixin(new Builder("LoadingScreenRendererSizeMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.LoadingScreenRendererSizeMixin")),
    GuiNewChatMouseOffsetMixin(new Builder("GuiNewChatMouseOffsetMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GuiNewChatMouseOffsetMixin")),
    MinecraftLoadingScreenSizeMixin(new Builder("MinecraftLoadingScreenSizeMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .addExcludedMod(TargetedMod.BETTER_LOADING_SCREEN)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.MinecraftLoadingScreenSizeMixin")),

    GuiVideoSettingsGUIScaleUpdateMixin(new Builder("GuiVideoSettingsGUIScaleUpdateMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .addExcludedMod(TargetedMod.OPTIFINE)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.minecraft.GuiVideoSettingsGUIScaleUpdateMixin")),

    GuiScreenGUIScaleUpdateMixin(new Builder("GuiScreenGUIScaleUpdateMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .addTargetedMod(TargetedMod.OPTIFINE)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.optifine.GuiScreenGUIScaleUpdateMixin")),
    TooltipProviderOptionsGUIScaleLangMixin(new Builder("TooltipProviderOptionsGUIScaleLangMixin")
        .addTargetedMod(TargetedMod.VANILLA)
        .addTargetedMod(TargetedMod.OPTIFINE)
        .setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .addMixinClasses("client.optifine.TooltipProviderOptionsGUIScaleLangMixin")),

    RenderUtilsScissorAlignmentMixin(new Builder("RenderUtilsScissorAlignmentMixin")
        .addTargetedMod(TargetedMod.BETTER_QUESTING)
        .setSide(Side.CLIENT)
        .setPhase(Phase.LATE)
        .addMixinClasses("client.betterquesting.RenderUtilsScissorAlignmentMixin"))
    ;

    private static final Logger LOGGER = LogManager.getLogger("RightProperGUIScale");

    private final List<String> mixinClasses;
    private final List<TargetedMod> targetedMods;
    private final List<TargetedMod> excludedMods;
    private final Phase phase;
    private final Side side;

    Mixins(Builder builder) {
        this.mixinClasses = builder.mixinClasses;
        this.targetedMods = builder.targetedMods;
        this.excludedMods = builder.excludedMods;
        this.phase = builder.phase;
        this.side = builder.side;
        if (this.mixinClasses.isEmpty()) {
            throw new RuntimeException("No mixin class specified for Mixin : " + this.name());
        }
        if (this.targetedMods.isEmpty()) {
            throw new RuntimeException("No targeted mods specified for Mixin : " + this.name());
        }
        if (this.phase == null) {
            throw new RuntimeException("No Phase specified for Mixin : " + this.name());
        }
        if (this.side == null) {
            throw new RuntimeException("No Side function specified for Mixin : " + this.name());
        }
    }

    public static List<String> getEarlyMixins(Set<String> loadedCoreMods) {
        final List<String> mixins = new ArrayList<>();
        final List<String> notLoading = new ArrayList<>();
        for (Mixins mixin : Mixins.values()) {
            if (mixin.phase == Phase.EARLY) {
                if (mixin.shouldLoad(loadedCoreMods, Collections.emptySet())) {
                    mixins.addAll(mixin.mixinClasses);
                } else {
                    notLoading.addAll(mixin.mixinClasses);
                }
            }
        }
        LOGGER.info("Not loading the following EARLY mixins: {}", notLoading.toString());
        return mixins;
    }

    public static List<String> getLateMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        final List<String> notLoading = new ArrayList<>();
        for (Mixins mixin : Mixins.values()) {
            if (mixin.phase == Phase.LATE) {
                if (mixin.shouldLoad(Collections.emptySet(), loadedMods)) {
                    mixins.addAll(mixin.mixinClasses);
                } else {
                    notLoading.addAll(mixin.mixinClasses);
                }
            }
        }
        LOGGER.info("Not loading the following LATE mixins: {}", notLoading.toString());
        return mixins;
    }

    private boolean shouldLoadSide() {
        return side == Side.BOTH || (side == Side.SERVER && FMLLaunchHandler.side().isServer())
            || (side == Side.CLIENT && FMLLaunchHandler.side().isClient());
    }

    private boolean allModsLoaded(List<TargetedMod> targetedMods, Set<String> loadedCoreMods, Set<String> loadedMods) {
        if (targetedMods.isEmpty()) return false;

        for (TargetedMod target : targetedMods) {
            if (target == TargetedMod.VANILLA) continue;

            // Check coremod first
            if (!loadedCoreMods.isEmpty() && target.coreModClass != null
                && !loadedCoreMods.contains(target.coreModClass))
                return false;
            else if (!loadedMods.isEmpty() && target.modId != null && !loadedMods.contains(target.modId)) return false;
        }

        return true;
    }

    private boolean noModsLoaded(List<TargetedMod> targetedMods, Set<String> loadedCoreMods, Set<String> loadedMods) {
        if (targetedMods.isEmpty()) return true;

        for (TargetedMod target : targetedMods) {
            if (target == TargetedMod.VANILLA) continue;

            // Check coremod first
            if (!loadedCoreMods.isEmpty() && target.coreModClass != null
                && loadedCoreMods.contains(target.coreModClass))
                return false;
            else if (!loadedMods.isEmpty() && target.modId != null && loadedMods.contains(target.modId)) return false;
        }

        return true;
    }

    private boolean shouldLoad(Set<String> loadedCoreMods, Set<String> loadedMods) {
        return (shouldLoadSide()
            && allModsLoaded(targetedMods, loadedCoreMods, loadedMods)
            && noModsLoaded(excludedMods, loadedCoreMods, loadedMods));
    }

    private static class Builder {

        private final String name;
        private final List<String> mixinClasses = new ArrayList<>();
        private final List<TargetedMod> targetedMods = new ArrayList<>();
        private final List<TargetedMod> excludedMods = new ArrayList<>();
        private Phase phase = null;
        private Side side = null;

        public Builder(String name) {
            this.name = name;
        }

        public Builder addMixinClasses(String... mixinClasses) {
            this.mixinClasses.addAll(Arrays.asList(mixinClasses));
            return this;
        }

        public Builder setPhase(Phase phase) {
            if (this.phase != null) {
                throw new RuntimeException("Trying to define Phase twice for " + this.name);
            }
            this.phase = phase;
            return this;
        }

        public Builder setSide(Side side) {
            if (this.side != null) {
                throw new RuntimeException("Trying to define Side twice for " + this.name);
            }
            this.side = side;
            return this;
        }

        public Builder addTargetedMod(TargetedMod mod) {
            this.targetedMods.add(mod);
            return this;
        }

        public Builder addExcludedMod(TargetedMod mod) {
            this.excludedMods.add(mod);
            return this;
        }
    }

    private enum Side {
        BOTH,
        CLIENT,
        SERVER
    }

    private enum Phase {
        EARLY,
        LATE,
    }
}
