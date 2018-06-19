package ca.graphica.visual;

import java.awt.*;
import java.util.ArrayList;

public class Text {
    private int colour = Colour.WHITE;
    private Color color = new Color(colour);

    ArrayList<String> texts = new ArrayList<String>();
    ArrayList<Double> x = new ArrayList<Double>();
    ArrayList<Double> y = new ArrayList<Double>();
    ArrayList<Integer> sizes = new ArrayList<Integer>();

    public Graphics g;

    public void setColour(Colour c){
        colour = c.colour;
    }

    public Text(){

    }

    public Text(Colour c){
        colour = c.colour;
    }

    public void drawText(String val, double x, double y, int size){
        texts.add(val);
        this.x.add(x);
        this.y.add(y);
        sizes.add(size);
    }

    public void rasterizeTexts(){
        g.setColor(color);

        for(int i = 0; i < texts.size(); i++){
            g.setFont(new Font("TimesRoman", Font.PLAIN, sizes.get(i)));
            g.drawString(texts.get(i), (int)x.get(i).doubleValue(), (int)y.get(i).doubleValue());
        }

        texts.clear();
        x.clear();
        y.clear();
    }
}
