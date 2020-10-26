package com.nodj;

import javax.swing.*;
import java.awt.*;

public class FormStation {
    public JFrame frame;
    private Station<ITransport, IDrawingDoors> station;
    private final JButton parkBusButton = new JButton("Припарковать автобус");
    private final JButton parkBusWithGarmoshkaButton = new JButton("Припарковать автобус с гармошкой");
    private final int numType = 0;

    /**
     * Launch the application.
     */
    FormStation() {
        initialize();
    }

    private void initialize() {
        int width = 700;
        int height = 400;
        station = new Station<>(width, height);
        frame = new JFrame();
        frame.setBounds(100, 100, 1200, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Автовокзал");
        StationPanel panel = new StationPanel(station);
        panel.setBounds(10, 11, width, height);
        frame.getContentPane().add(panel);
        parkBusButton.addActionListener(e -> {
            Color mainColor = JColorChooser.showDialog(frame, "Выберите цвет автобуса", Color.BLUE);
            if (mainColor != null) {
                var bus = new Bus(100, 1000, mainColor);
                if (station.add(bus)) {
                    panel.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Автовокзал переполнен", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            panel.repaint();
        });
        parkBusButton.setBounds(width + 20, 20, 200, 50);
        frame.getContentPane().add(parkBusButton);
        parkBusWithGarmoshkaButton.addActionListener(e -> {
            Color mainColor = JColorChooser.showDialog(frame, "Выберите цвет автобуса", Color.BLUE);
            if (mainColor != null) {
                Color dopColor = JColorChooser.showDialog(frame, "Выберите цвет дополнительный автобуса", Color.BLUE);
                if (dopColor != null) {
                    var bus = new BusWithGarmoshka(100, 1000, mainColor, dopColor, 320, 60, true, true, 3, numType);
                    if (station.add(bus)) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Автовокзал переполнен", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            panel.repaint();
        });
        parkBusWithGarmoshkaButton.setBounds(width + 20, 80, 200, 50);
        frame.getContentPane().add(parkBusWithGarmoshkaButton);
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanel.setBorder(BorderFactory.createTitledBorder("Забрать машину"));
        groupPanel.setBounds(width + 20, 200, 200, 100);
        frame.getContentPane().add(groupPanel);
        JLabel placeLabel = new JLabel("Место:");
        groupPanel.add(placeLabel);
        JTextField placeTextField = new JTextField(2);
        placeTextField.setFont(placeTextField.getFont().deriveFont(20f));
        placeTextField.setBounds(20, 10, 100, 20);
        groupPanel.add(placeTextField);
        JButton takeBus = new JButton("Забрать");
        takeBus.addActionListener(e -> {
            if (!placeTextField.getText().equals("")) {
                int numPlace;
                try {
                    numPlace = Integer.parseInt(placeTextField.getText());
                } catch (Exception ex) {
                    return;
                }
                var bus = station.remove(numPlace);
                if (bus != null) {
                    EventQueue.invokeLater(() -> {
                        FormBus window;
                        try {
                            window = new FormBus(frame);
                            window.frame.setVisible(true);
                            frame.setVisible(false);
                        } catch (Exception exp) {
                            exp.printStackTrace();
                            return;
                        }
                        window.setTransport(bus);
                    });
                }
                panel.repaint();
            }
        });
        takeBus.setBounds(20, 30, 100, 50);
        groupPanel.add(takeBus);

        JPanel groupPanelGuess = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelGuess.setBorder(BorderFactory.createTitledBorder("Угадайка"));
        groupPanelGuess.setBounds(width + 20, 300, 300, 150);
        frame.getContentPane().add(groupPanelGuess);
        JLabel amountLabel = new JLabel("Количество:");
        groupPanelGuess.add(amountLabel);
        JTextField amountTextField = new JTextField(2);
        amountTextField.setFont(amountTextField.getFont().deriveFont(20f));
        amountTextField.setBounds(20, 10, 100, 20);
        groupPanelGuess.add(amountTextField);
        JButton guessOccupied = new JButton("Предположить сколько мест занято");
        guessOccupied.addActionListener(e -> {
            if (!amountTextField.getText().equals("")) {
                int amountPlaces;
                try {
                    amountPlaces = Integer.parseInt(amountTextField.getText());
                } catch (Exception ex) {
                    return;
                }
                if (station.equal(amountPlaces))
                    JOptionPane.showMessageDialog(frame, "Вы угадали! Автобусов на автовокзале " + amountPlaces, "Результат", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(frame, "Вы не угадали!", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        guessOccupied.setBounds(20, 30, 100, 50);
        groupPanelGuess.add(guessOccupied);
        JButton guessFreeButton = new JButton("Предположить сколько мест свободно");
        guessFreeButton.addActionListener(e -> {
            if (!amountTextField.getText().equals("")) {
                int amountPlaces;
                try {
                    amountPlaces = Integer.parseInt(amountTextField.getText());
                } catch (Exception ex) {
                    return;
                }
                if (station.nonEqual(amountPlaces))
                    JOptionPane.showMessageDialog(frame, "Вы угадали! Свободных мест на автовокзале " + amountPlaces, "Результат", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(frame, "Вы не угадали!", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        guessFreeButton.setBounds(20, 30, 100, 50);
        groupPanelGuess.add(guessFreeButton);
    }
}
