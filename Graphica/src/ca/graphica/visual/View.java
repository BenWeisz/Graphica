package ca.graphica.visual;

import ca.graphica.container.Graphica;

public class View {
    public static int VIEW_X;
    public static int VIEW_Y;

    private Graphica g;

    public View(Graphica g){
        this.g = g;

        VIEW_X = g.PLOTWIDTH / 2;
        VIEW_Y = g.PLOTHEIGHT / 2;
    }
}
