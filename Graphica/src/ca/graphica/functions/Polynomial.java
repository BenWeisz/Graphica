package ca.graphica.functions;

import ca.graphica.container.Graphica;
import ca.graphica.visual.Colour;
import ca.graphica.container.Plotter;
import ca.graphica.visual.shapes.Line;
import ca.graphica.visual.shapes.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import ca.weisz.jfunc.*;

public class Polynomial {
    private ArrayList<Vector> fxValues = new ArrayList<Vector>();
    private ArrayList<Shape> lines = new ArrayList<Shape>();

    private Graphica g;
    private int colour = Colour.WHITE;
    private String func;

    private void init(){
        loadFunction(func , -(Plotter.H_UNITS / 2), Plotter.H_UNITS / 2, 0.2);

        for(int i = 0; i < fxValues.size() - 1; i++){
            try{
                lines.add(new Line(fxValues.get(i).getVector2()[0], fxValues.get(i).getVector2()[1], fxValues.get(i + 1).getVector2()[0], fxValues.get(i + 1).getVector2()[1], colour, g));
            }catch (Exception e){}
        }
    }

    public void rasterize(){
        for(int i = 0; i < lines.size(); i++){
            try {
                lines.get(i).rasterize();
            }catch(Exception e){
            }
        }
    }

    private void loadFunction(String funcIn, double x1, double x2, double dx){
        File template = new File("template.txt");
        File function = new File((char)(65 + g.plotter.FUNCTION_COUNT) + ".java");

        String func = Parser.parse(funcIn);

        String[] verticies = new String[(int)((x2 - x1) / dx) * 2];

        //Function Creation
        PrintWriter out;
        Scanner in;
        int FLAG_COUNT = 0;
        try{
            in = new Scanner(template);
            out = new PrintWriter(function);

            String line;
            while(in.hasNext()){
                line = in.nextLine().trim();
                if(!line.equals("#"))
                    out.println(line);
                else{
                    if(FLAG_COUNT == 0){
                        out.print((char)(65 + g.plotter.FUNCTION_COUNT));
                        FLAG_COUNT++;
                    }
                    else{
                        out.println(func);
                        FLAG_COUNT++;
                    }
                }
            }

            out.close();
        }catch(Exception e){}

        //Function Compilation
        try {
            Process funcCompile = Runtime.getRuntime().exec("cmd /c javac " + (char)(65 + g.plotter.FUNCTION_COUNT) + ".java");
            funcCompile.waitFor();
            Process run = Runtime.getRuntime().exec("cmd /c java " + (char)(65 + g.plotter.FUNCTION_COUNT) + " " + x1 + " " + x2 + " " + dx);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(run.getInputStream()));
            String msgs;
            while ((msgs = stdInput.readLine()) != null) verticies = msgs.trim().split(" ");//verticies = msgs.trim().split(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < verticies.length / 2; i++)
            fxValues.add(new Vector2(Double.parseDouble(verticies[i * 2]), Double.parseDouble(verticies[(i * 2) + 1])));

        g.plotter.FUNCTION_COUNT++;
    }

    public Polynomial(String func, int colour, Graphica g){
        this.g = g;
        this.colour = colour;
        this.func = func;
        init();
    }

    public Polynomial(String func, Graphica g){
        this.g = g;
        this.func = func;
        init();
    }
}