package ca.graphica.functions;

public class Vector2 implements Vector {
    private double x, y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double[] getVector2(){
        double[] vec = {x, y};

        return vec;
    }
}
