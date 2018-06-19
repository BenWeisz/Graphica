package ca.graphica.container;

import ca.graphica.functions.Polynomial;
import ca.graphica.visual.Colour;
import ca.graphica.visual.View;
import ca.graphica.visual.shapes.Line;

import javax.swing.*;
import java.util.ArrayList;

public class Plotter {
    public Graphica g;

    private static View view;
    public static double SCALE = 1.0;
    public static int FUNCTION_COUNT = 0;

    private Line plot_h, plot_v;
    public static int H_UNITS = Graphica.PLOTWIDTH / scaledUnit() + 1, V_UNITS = Graphica.PLOTHEIGHT / scaledUnit() + 1;

    private Line[] marks = new Line[H_UNITS + V_UNITS];
    private Polynomial p;
    public ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();

    public void init() {
        plot_h = new Line(-(H_UNITS / 2), 0, (H_UNITS / 2), 0, g);
        plot_v = new Line(0, (V_UNITS / 2), 0, -(V_UNITS / 2), g);

        //Horizontal
        for(int i = -(H_UNITS / 2); i < (H_UNITS / 2) + 1; i++){
            try{
                marks[i + (H_UNITS / 2)] = new Line(i, 0, i, -0.1, g);

                int[] gCoords = toGCoords(i - 0.2, -0.6);
                g.TEXT.drawText("" + i, gCoords[0], gCoords[1], 12);
            }catch (Exception e){

            }
        }

        //Vertical
        for(int i = -(V_UNITS / 2); i < (V_UNITS / 2) + 1; i++) {
            try {
                marks[i + (V_UNITS / 2) + H_UNITS] = new Line(0, i, 0.1, i, g);

                int[] gCoords = toGCoords(0.3, i - 0.15);
                g.TEXT.drawText("" + i, gCoords[0], gCoords[1], 12);
            } catch (Exception e) {
            }
        }
    }

    public void rasterize(){
        plot_h.rasterize();
        plot_v.rasterize();

        for(int i = 0; i < marks.length; i++)
            marks[i].rasterize();

        for(int i = 0; i < polynomials.size(); i++)
            polynomials.get(i).rasterize();

        //Horizontal
        for(int i = -(H_UNITS / 2); i < (H_UNITS / 2) + 1; i++){
            try{
                int[] gCoords = toGCoords(i - 0.2, -0.6);
                g.TEXT.drawText("" + i, gCoords[0], gCoords[1], 12);
            }catch (Exception e){

            }
        }

        //Vertical
        for(int i = -(V_UNITS / 2); i < (V_UNITS / 2) + 1; i++) {
            try {
                int[] gCoords = toGCoords(0.3, i - 0.15);
                g.TEXT.drawText("" + i, gCoords[0], gCoords[1], 12);
            } catch (Exception e) {
            }
        }
    }

    //Calculate Amount Of Pixels For One Graph Unit
    public static int scaledUnit(){
        return Graphica.PLOTHEIGHT / (int)(Math.pow(10, SCALE) * 2);
    }

    //Convert Cartesian Coordinates To Graphics Coordinates
    public static int[] toGCoords(double x, double y){
        int[] gCoords = new int[2];
        int pixelsPerUnit = Plotter.scaledUnit();

        gCoords[0] = (int)(x * pixelsPerUnit) + view.VIEW_X;
        gCoords[1] = -(int)(y * pixelsPerUnit) + view.VIEW_Y;

        return gCoords;
    }

    public Plotter(Graphica g){
        this.g = g;
        view = new View(g);

        init();
    }
}
