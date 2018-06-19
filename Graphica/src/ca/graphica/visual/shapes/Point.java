package ca.graphica.visual.shapes;

import ca.graphica.container.Graphica;
import ca.graphica.visual.Colour;

public class Point{
    //FIX POINT CLASS TO WORK BASED ON CARTESIAN COORDINATES

    private Graphica g;

    private float x, y;
    private boolean open = true;

    private int[] shape_open_x = {5, 6, 7, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 8, 9, 10, 1, 2, 10, 11, 1, 2, 10, 11, 0, 1, 11, 12, 0, 1, 11, 12, 0, 1, 11, 12, 1, 2, 10, 11, 1, 2, 10, 11, 2, 3, 4, 8, 9, 10, 3, 4, 5, 6, 7, 8, 9, 5, 6, 7};
    private int[] shape_open_y = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12};

    private int[] shape_closed_x = {5, 6, 7, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 3, 4, 5, 6, 7, 8, 9, 5, 6, 7};
    private int[] shape_closed_y = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12};

    private int colour = Colour.WHITE;

    public Point(float x, float y, Graphica g){
        this.g = g;

        //Transform Outer Shape
        for(int i = 0; i < shape_open_x.length; i++){
            shape_open_x[i] += x;
            shape_open_y[i] += y;
        }

        //Transform Closed Shape
        for(int i = 0; i < shape_closed_x.length; i++){
            shape_closed_x[i] += x;
            shape_closed_y[i] += y;
        }
    }

    public Point(float x, float y, Colour c, Graphica g){
        this.g = g;

        //Transform Open Shape
        for(int i = 0; i < shape_open_x.length; i++){
            shape_open_x[i] += x;
            shape_open_y[i] += y;
        }

        //Transform Closed Shape
        for(int i = 0; i < shape_closed_x.length; i++){
            shape_closed_x[i] += x;
            shape_closed_y[i] += y;
        }

        colour = c.colour;
    }

    public Point(float x, float y, int c, Graphica g){
        this.g = g;

        //Transform Open Shape
        for(int i = 0; i < shape_open_x.length; i++){
            shape_open_x[i] += x;
            shape_open_y[i] += y;
        }

        //Transform Closed Shape
        for(int i = 0; i < shape_closed_x.length; i++){
            shape_closed_x[i] += x;
            shape_closed_y[i] += y;
        }

        colour = c;
    }

    public void rasterize(){
        if(open){
            for(int i = 0; i < shape_open_x.length; i++){
                g.pixels[(Graphica.PLOTWIDTH * shape_open_y[i]) + shape_open_x[i]] = colour;
            }
        }
        else{
            for(int i = 0; i < shape_closed_x.length; i++){
                g.pixels[(Graphica.PLOTWIDTH * shape_closed_y[i]) + shape_closed_x[i]] = colour;
            }
        }
    }

    public void setColour(Colour c){
        colour = c.colour;
    }

    public void setBehaviour(boolean val){
        open = val;
    }
}
