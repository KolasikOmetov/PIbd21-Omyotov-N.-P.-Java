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
    private final JButton createBusButton = new JButton("Create Bus");
    private final JButton createBusWithGarmoshkaButton = new JButton("Create BusWithGarmoshka");
    private ITransport transport;
    private int numType = 1;

    /**
     * Launch the application.
     */
    App() {
        initialize();
    }

    private void initialize() {
        int width = 950;
        int height = 400;
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        BusPanel panel = new BusPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED,
                null, null, null, null));
        panel.setBounds(10, 11, width, height);
        frame.getContentPane().add(panel);
        setupButton(up, "up", 65, height + 30);
        setupButton(down, "down", 65, height + 90);
        setupButton(left, "left", 5, height + 90);
        setupButton(right, "right", 125, height + 90);
        createBusButton.addActionListener(e -> {
            transport = new Bus(100, 1000, Color.BLUE);
            transport.setPosition(10, 10, width, height);
            panel.setTransport(transport);
            panel.repaint();
        });
        createBusButton.setBounds(200, height + 45, 100, 50);
        frame.getContentPane().add(createBusButton);
        createBusWithGarmoshkaButton.addActionListener(e -> {
            transport = new BusWithGarmoshka(100, 1000, Color.BLUE, Color.green, 320, 60, true, true, 3, numType);
            transport.setPosition(10, 10, width, height);
            panel.setTransport(transport);
            panel.repaint();
        });
        createBusWithGarmoshkaButton.setBounds(500, height + 45, 100, 50);
        frame.getContentPane().add(createBusWithGarmoshkaButton);
        up.addActionListener(e -> {
            if (transport != null) {
                transport.moveTransport(Direction.Up);
                panel.repaint();
            }
        });
        down.addActionListener(e -> {
            if (transport != null) {
                transport.moveTransport(Direction.Down);
                panel.repaint();
            }
        });
        left.addActionListener(e -> {
            if (transport != null) {
                transport.moveTransport(Direction.Left);
                panel.repaint();
            }
        });
        right.addActionListener(e -> {
            if (transport != null) {
                transport.moveTransport(Direction.Right);
                panel.repaint();
            }
        });

        JButton threeDoors = new JButton("3");
        threeDoors.setBounds(310, height + 45, 50, 50);
        frame.getContentPane().add(threeDoors);
        threeDoors.addActionListener(e -> {
            if (transport != null && transport.getClass() == BusWithGarmoshka.class) {
                BusWithGarmoshka bus = (BusWithGarmoshka) transport;
                bus.setNumDoors(3);
            }
            panel.repaint();
        });

        JButton fourDoors = new JButton("4");
        fourDoors.setBounds(370, height + 45, 50, 50);
        frame.getContentPane().add(fourDoors);
        fourDoors.addActionListener(e -> {

            if (transport != null && transport.getClass() == BusWithGarmoshka.class) {
                BusWithGarmoshka bus = (BusWithGarmoshka) transport;
                bus.setNumDoors(4);
            }
            panel.repaint();
        });

        JButton fiveDoors = new JButton("5");
        fiveDoors.setBounds(430, height + 45, 50, 50);
        frame.getContentPane().add(fiveDoors);
        fiveDoors.addActionListener(e -> {
            if (transport != null && transport.getClass() == BusWithGarmoshka.class) {
                BusWithGarmoshka bus = (BusWithGarmoshka) transport;
                bus.setNumDoors(5);
            }
            panel.repaint();
        });

        JButton standardDoors = new JButton("Standard Doors");
        standardDoors.setBounds(310, height + 100, 200, 50);
        frame.getContentPane().add(standardDoors);
        standardDoors.addActionListener(e -> {
            numType = 1;
        });

        JButton ovalDoors = new JButton("Oval Doors");
        ovalDoors.setBounds(370, height + 160, 200, 50);
        frame.getContentPane().add(ovalDoors);
        ovalDoors.addActionListener(e -> {
            numType = 2;
        });

        JButton doubleOvalDoors = new JButton("DoubleOval Doors");
        doubleOvalDoors.setBounds(430, height + 220, 200, 50);
        frame.getContentPane().add(doubleOvalDoors);
        doubleOvalDoors.addActionListener(e -> {
            numType = 3;
        });

    }

    void setupButton(JButton button, String name, int x, int y) {
        try {
            Image img = ImageIO.read(new File("src/res/" + name + ".png"));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        button.setBounds(x, y, 50, 50);
        frame.getContentPane().add(button);
    }
}
