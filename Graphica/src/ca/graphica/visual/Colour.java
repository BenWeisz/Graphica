package ca.graphica.visual;

public class Colour {
    public int r, g, b;
    public int colour;

    public static final int BLACK = new Colour(0, 0, 0).colour;
    public static final int PLOT = new Colour(75, 75, 75).colour;
    public static final int WHITE = new Colour(240, 240, 240).colour;
    public static final int GREEN = new Colour(0, 255, 0).colour;
    public static final int UI_DARK_GREY = new Colour(195, 195, 195).colour;
    public static final int UI_LIGHT_GREY = new Colour(240, 240, 240).colour;

    public Colour(int hex){
        r = (hex & 0xFF0000) >> 16;
        g = (hex & 0x00FF00) >> 8;
        b = hex & 0x0000FF;

        colour = hex;
    }

    public Colour(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;

        colour = (r << 16) | (g << 8) | b;
    }
}