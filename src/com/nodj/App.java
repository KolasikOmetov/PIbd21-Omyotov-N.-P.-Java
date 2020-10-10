package com.nodj;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;

public class App {
    public JFrame frame;
    private final JButton up = new JButton();
    private final JButton down = new JButton();
    private final JButton left = new JButton();
    private final JButton right = new JButton();
    private final JButton createButton = new JButton("Create");
    private BusWithGarmoshka busWithGarmoshka;

    /**
     * Launch the application.
     */
    App() {
        initialize();
    }

    private void initialize() {
        int width = 950;
        int height = 600;
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        BusPanel panel = new BusPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED,
                null, null, null, null));
        panel.setBounds(10, 11, width, height);
        frame.getContentPane().add(panel);
        setupButton(up, "up", 65, 630);
        setupButton(down, "down", 65, 690);
        setupButton(left, "left", 5, 690);
        setupButton(right, "right", 125, 690);
        createButton.addActionListener(e -> {
            busWithGarmoshka = new BusWithGarmoshka(100, 1000, Color.BLUE, Color.YELLOW, true, true);
            busWithGarmoshka.setPosition(10, 10, width, height);
            panel.setBusWithGarmoshka(busWithGarmoshka);
            panel.repaint();
        });
        createButton.setBounds(200, 645, 100, 50);
        frame.getContentPane().add(createButton);
        up.addActionListener(e -> {
            if(busWithGarmoshka != null){
                busWithGarmoshka.moveTransport(Direction.Up);
                panel.repaint();
            }
        });
        down.addActionListener(e -> {
            if(busWithGarmoshka != null){
                busWithGarmoshka.moveTransport(Direction.Down);
                panel.repaint();
            }
        });
        left.addActionListener(e -> {
            if(busWithGarmoshka != null){
                busWithGarmoshka.moveTransport(Direction.Left);
                panel.repaint();
            }
        });
        right.addActionListener(e -> {
            if(busWithGarmoshka != null){
                busWithGarmoshka.moveTransport(Direction.Right);
                panel.repaint();
            }
        });

        JButton threeDoors = new JButton("3");
        threeDoors.setBounds(310, 645, 50, 50);
        frame.getContentPane().add(threeDoors);
        threeDoors.addActionListener(e -> {
            busWithGarmoshka.drawingDoors.setConfig(3);
            panel.repaint();
        });

        JButton fourDoors = new JButton("4");
        fourDoors.setBounds(370, 645, 50, 50);
        frame.getContentPane().add(fourDoors);
        fourDoors.addActionListener(e -> {
            busWithGarmoshka.drawingDoors.setConfig(4);
            panel.repaint();
        });

        JButton fiveDoors = new JButton("5");
        fiveDoors.setBounds(430, 645, 50, 50);
        frame.getContentPane().add(fiveDoors);
        fiveDoors.addActionListener(e -> {
            busWithGarmoshka.drawingDoors.setConfig(5);
            panel.repaint();
        });
    }

    void setupButton(JButton button, String name, int x, int y) {
        try {
            Image img = ImageIO.read(new File("src/res/" + name + ".png"));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        button.setBounds(x, y, 50, 50);
        frame.getContentPane().add(button);
    }
}
