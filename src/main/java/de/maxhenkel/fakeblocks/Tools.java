package de.maxhenkel.fakeblocks;

public class Tools {

    public static float getRed(int color) {
        return (float) (color >> 16 & 255) / 255F;
    }

    public static float getGreen(int color) {
        return (float) (color >> 8 & 255) / 255F;
    }

    public static float getBlue(int color) {
        return (float) (color & 255) / 255F;
    }

}
