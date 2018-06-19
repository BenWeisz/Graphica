package ca.graphica.visual.shapes;

import ca.graphica.container.Graphica;
import ca.graphica.visual.Colour;
import ca.graphica.container.Plotter;

public class Line implements Shape{

    private Graphica g;

    private int colour = Colour.WHITE;

    public double[] cartPoints = new double[4];

    private double m;
    private double b;

    private boolean vertical = false;
    private int verticalDir;

    private void init(){
        if(cartPoints[2] - cartPoints[0] != 0) {
            m = (cartPoints[3] - cartPoints[1]) / (cartPoints[2] - cartPoints[0]);
            b = cartPoints[1] - (m * cartPoints[0]);
        }
        else{
            vertical = true;
            if(cartPoints[1] > cartPoints[3])
                verticalDir = -1;
            else verticalDir = 1;

            if(cartPoints[1] > cartPoints[3]){
                double temp = cartPoints[1];
                cartPoints[1] = cartPoints[3];
                cartPoints[3] = temp;
            }
        }

        if(cartPoints[0] > cartPoints[2]){
            double temp = cartPoints[0];
            cartPoints[0] = cartPoints[2];
            cartPoints[2] = temp;
        }
    }

    public void rasterize(){
        if(!vertical){
            for(double x = cartPoints[0]; x <= cartPoints[2]; x+=0.001){
                double y = (m * x) + b;
                int[] gCoords = Plotter.toGCoords(x, y);
                try{
                    if(!(gCoords[0] < 0 || gCoords[0] > g.PLOTWIDTH)){
                        g.pixels[(gCoords[1] * g.PLOTWIDTH) + gCoords[0]] = colour;
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    if(g.DEBUG) System.out.println("Error: Rasterizing Line :: Non-Vertical");
                }
            }
        }
        else{
            double x = cartPoints[0];
            for(double y = cartPoints[1]; y <= cartPoints[3]; y+=0.001){
                int[] gCoords = Plotter.toGCoords(x, y);

                try{
                    if(!(gCoords[0] < 0 || gCoords[0] > g.PLOTWIDTH))
                        g.pixels[(gCoords[1] * g.PLOTWIDTH) + gCoords[0]] = colour;
                }catch(ArrayIndexOutOfBoundsException e){
                    if(g.DEBUG) System.out.println("Error: Rasterizing Line :: Vertical");
                }
            }
        }
    }

    public double[] getCart() {
        return cartPoints;
    }

    public Line(double x1, double y1, double x2, double y2, Graphica g){
        this.g = g;

        cartPoints[0] = x1;
        cartPoints[1] = y1;
        cartPoints[2] = x2;
        cartPoints[3] = y2;

        init();
    }

    public Line(double x1, double y1, double x2, double y2, int colour, Graphica g){
        this.g = g;
        this.colour = colour;

        cartPoints[0] = x1;
        cartPoints[1] = y1;
        cartPoints[2] = x2;
        cartPoints[3] = y2;

        init();
    }

    public void setColour(Colour c){
        colour = c.colour;
    }
}