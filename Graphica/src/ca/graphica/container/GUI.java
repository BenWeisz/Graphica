package ca.graphica.container;

import ca.graphica.functions.Polynomial;
import ca.graphica.visual.Colour;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    private Graphica g;

    private ArrayList<String> equations = new ArrayList<String>();
    private JTextField bar;

    private JButton add, delete;

    private JButton sin, cos, tan, sqrt, pow, pi;

    private JSlider rc = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
    private JSlider gc = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
    private JSlider bc = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);

    private JPanel table = new JPanel();

    private ArrayList<JPanel> cols = new ArrayList<JPanel>();
    private ArrayList<JTextField> f = new ArrayList<JTextField>();
    private ArrayList<JTextField> e = new ArrayList<JTextField>();

    public GUI(Graphica g){
        this.g = g;

        JFrame eq = new JFrame("Equation Builder");

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel eqLabel = new JLabel("Function: ");
        eqLabel.setBounds(10, 10, 100, 20);

        add = new JButton("Add");
        delete = new JButton("Delete");
        add.setBounds(50, 50, 75, 30);
        add.addActionListener(this);
        add.setActionCommand("add");
        delete.setBounds(150, 50, 75, 30);

        bar = new JTextField(10);
        bar.setBounds(75, 10, 200, 25);

        sin = new JButton("sin");
        cos = new JButton("cos");
        tan = new JButton("tan");
        pow = new JButton("x^y");
        sqrt = new JButton("sqrt");
        pi = new JButton("PI");

        sin.addActionListener(this);
        cos.addActionListener(this);
        tan.addActionListener(this);
        pow.addActionListener(this);
        sqrt.addActionListener(this);
        pi.addActionListener(this);

        sin.setActionCommand("sin");
        cos.setActionCommand("cos");
        tan.setActionCommand("tan");
        pow.setActionCommand("pow");
        sqrt.setActionCommand("sqrt");
        pi.setActionCommand("pi");

        int bx = 350, by = 20;
        sin.setBounds(bx, by, 75, 30);
        cos.setBounds(bx + 75, by, 75, 30);
        tan.setBounds(bx + 150, by, 75, 30);
        pow.setBounds(bx, by + 30, 75, 30);
        sqrt.setBounds(bx + 75, by + 30, 75, 30);
        pi.setBounds(bx + 150, by + 30, 75, 30);

        rc.setValue(255);
        gc.setValue(255);
        bc.setValue(255);

        rc.setBounds(405, 107, 175, 25);
        gc.setBounds(405, 137, 175, 25);
        bc.setBounds(405, 167, 175, 25);

        JPanel c = new JPanel();
        c.setBounds(300, 125, 50, 50);
        c.setBackground(new Color(255, 255, 255));

        rc.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                c.setBackground(new Color(rc.getValue(), gc.getValue(), bc.getValue()));
            }
        });
        gc.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                c.setBackground(new Color(rc.getValue(), gc.getValue(), bc.getValue()));
            }
        });
        bc.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                c.setBackground(new Color(rc.getValue(), gc.getValue(), bc.getValue()));
            }
        });

        JLabel rl = new JLabel("R"), gl = new JLabel("G"), bl = new JLabel("B");
        rl.setBounds(385, 107, 25, 25);
        gl.setBounds(385, 137, 25, 25);
        bl.setBounds(385, 167, 25, 25);

        //Func Table
        table.setBounds(20, 200, 595, 225);
        //Func Table End

        panel.add(table);
        panel.add(rl);
        panel.add(gl);
        panel.add(bl);
        panel.add(rc);
        panel.add(gc);
        panel.add(bc);
        panel.add(c);
        panel.add(eqLabel);
        panel.add(bar);
        panel.add(add);
        panel.add(delete);
        panel.add(sin);
        panel.add(cos);
        panel.add(tan);
        panel.add(pow);
        panel.add(sqrt);
        panel.add(pi);
        eq.setContentPane(panel);

        eq.setSize(new Dimension(640, 480));
        eq.setVisible(true);
        eq.setLocation(g.frame.getX() + g.frame.getWidth() + 10, g.frame.getY() + (g.frame.getHeight() / 2) - (eq.getHeight() / 2));
        eq.setResizable(false);
        eq.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addFunc(Color c, String f, String e){
        JPanel cPanel = new JPanel();
        cPanel.setBackground(c);
        cols.add(cPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            String in = bar.getText();

            boolean not = true;
            for (int i = 0; i < equations.size(); i++) {
                if (in.equals(equations.get(i))) {
                    not = false;
                    break;
                }
            }

            if (not) {
                equations.add(in);
                g.plotter.polynomials.add(new Polynomial(in, new Colour(rc.getValue(), gc.getValue(), bc.getValue()).colour, g));
                g.RENDER_FLAG = true;
            }
        }
        else if(e.getActionCommand().equals("sin"))
            bar.setText(bar.getText() + "sin(");
        else if(e.getActionCommand().equals("cos"))
            bar.setText(bar.getText() + "cos(");
        else if(e.getActionCommand().equals("tan"))
            bar.setText(bar.getText() + "tan(");
        else if(e.getActionCommand().equals("pow"))
            bar.setText(bar.getText() + "^");
        else if(e.getActionCommand().equals("sqrt"))
            bar.setText(bar.getText() + "sqrt(");
        else if(e.getActionCommand().equals("pi"))
            bar.setText(bar.getText() + "PI");
    }
}
