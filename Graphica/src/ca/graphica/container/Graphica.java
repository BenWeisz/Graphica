package ca.graphica.container;

import ca.graphica.visual.Colour;
import ca.graphica.visual.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Graphica extends Canvas implements Runnable {

    private boolean running = false;

    private static final double VERSION = 0.1;
    private static final String TITLE = "Graphica DEV BUILD " + VERSION;

    private static final int SCALE = 300;
    public static final int WIDTH = 4 * SCALE;
    public static final int HEIGHT = 3 * SCALE;
    private static final int UPS = 60;

    public static final int PLOTWIDTH = WIDTH;
    public static final int PLOTHEIGHT = HEIGHT - (HEIGHT / 4);
    public static final boolean DEBUG = false;

    private Thread thread;
    public static JFrame frame;
    public Plotter plotter;
    public GUI gui;
    public  static Text TEXT;
    private BufferedImage image = new BufferedImage(PLOTWIDTH, PLOTHEIGHT,BufferedImage.TYPE_INT_RGB);
    public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public static boolean RENDER_FLAG = true;

    private void init(){
        TEXT = new Text();
        plotter = new Plotter(this);
        gui = new GUI(this);
    }

    private void update(){
        //plotter.update();
        //gui.update();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        //Plotter Graphics
        if(RENDER_FLAG) {
            clearRaster(Colour.PLOT);

            //Prepare Pixel Array
            plotter.rasterize();

            //Draw
            g.drawImage(image, 0, 0, PLOTWIDTH, PLOTHEIGHT, null);

            //Text
            TEXT.g = g;
            TEXT.rasterizeTexts();

            RENDER_FLAG = false;
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Graphica grapher = new Graphica();

        frame = new JFrame(TITLE);
        frame.setMinimumSize(new Dimension(PLOTWIDTH + 6, PLOTHEIGHT + 29));
        frame.add(grapher);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(15, 175);
        frame.setVisible(true);

        grapher.start();
    }

    public void run() {
        init();

        long lastTime = System.nanoTime();
        final double amountOfTicks = UPS;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(running){
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1){
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){

                timer += 1000;
                System.out.println("FPS " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void start(){
        if(running)
            return;

        running = true;

        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        if (!running)
            return;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private void clearRaster(int colour){
        for (int y = 0; y < PLOTHEIGHT; y++) {
            for (int x = 0; x < PLOTWIDTH; x++) {
                pixels[(y * PLOTWIDTH) + x] = colour;
            }
        }
    }
}
