package com.github.basdxz.rightproperguiscale.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class RightProperGUIScaleConfig {
    public static float GUI_SCALE_MIN = 1.0F;
    public static float GUI_SCALE_MAX = 10F;
    public static float GUI_SCALE_STEP = 0.1F;
    public static float GUI_SCALE_DEFAULT = 4.0F;

    public static int MIN_SCALED_WIDTH = 320;
    public static int MIN_SCALED_HEIGHT = 240;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        GUI_SCALE_MIN = configuration.getFloat("GUI_SCALE_MIN", Configuration.CATEGORY_GENERAL, GUI_SCALE_MIN, 0.1F, 1000.0F, "Minimum setting of the GUI Scale.");
        GUI_SCALE_MAX = configuration.getFloat("GUI_SCALE_MAX", Configuration.CATEGORY_GENERAL, GUI_SCALE_MAX, 1.0F, 1000.0F, "Maximum setting of the GUI Scale.");
        GUI_SCALE_STEP = configuration.getFloat("GUI_SCALE_STEP", Configuration.CATEGORY_GENERAL, GUI_SCALE_STEP, 0.01F, 1000.0F, "The step size of the GUI Scale slider.");
        GUI_SCALE_DEFAULT = configuration.getFloat("GUI_SCALE_DEFAULT", Configuration.CATEGORY_GENERAL, GUI_SCALE_DEFAULT, 0.1F, 1000.0F, "The Default GUI Scale.");

        MIN_SCALED_WIDTH = configuration.getInt("MIN_SCALED_WIDTH", Configuration.CATEGORY_GENERAL, MIN_SCALED_WIDTH, 80, 15360, "The minimum width the GUI will scale to.");
        MIN_SCALED_HEIGHT = configuration.getInt("MIN_SCALED_HEIGHT", Configuration.CATEGORY_GENERAL, MIN_SCALED_HEIGHT, 60, 8640, "The minimum height the GUI will scale to.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
