package com.nodj;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class FormBusConfig {
    public JDialog frame;
    public Vehicle bus = null;
    private IBusDelegate eventAddBus;

    FormBusConfig(JFrame parentFrame) {
        initialize(parentFrame);
    }

    private void initialize(JFrame parentFrame) {
        frame = new JDialog(parentFrame);
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Добавление автобуса");

        //groupPanelCusov
        JPanel groupPanelCusov = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelCusov.setBorder(BorderFactory.createTitledBorder("Тип кузова"));
        groupPanelCusov.setBounds(10, 10, 170, 85);
        frame.getContentPane().add(groupPanelCusov);

        setBusLabels(groupPanelCusov, "Обычный автобус");
        setBusLabels(groupPanelCusov, "Автобус с гармошкой");
        //groupPanelCusov

        //groupPanelOptions
        JPanel groupPanelOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelOptions.setBorder(BorderFactory.createTitledBorder("Параметры"));
        groupPanelOptions.setBounds(10, 100, 170, 240);
        frame.getContentPane().add(groupPanelOptions);

        JLabel maxSpeedLabel = new JLabel("Максимальная скорость");
        maxSpeedLabel.setBounds(10, 10, 280, 50);
        groupPanelOptions.add(maxSpeedLabel);

        JSpinner maxSpeedSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 1000, 1));
        maxSpeedSpinner.setBounds(150, 70, 50, 50);
        groupPanelOptions.add(maxSpeedSpinner);

        JLabel weightLabel = new JLabel("Вес автобуса");
        weightLabel.setBounds(10, 130, 280, 50);
        groupPanelOptions.add(weightLabel);

        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 1000, 1));
        weightSpinner.setBounds(150, 190, 50, 50);
        groupPanelOptions.add(weightSpinner);

        JCheckBox checkBoxGarmoshka = new JCheckBox("Гармошка");
        groupPanelOptions.add(checkBoxGarmoshka);

        JCheckBox checkBoxBackDoors = new JCheckBox("Задние двери");
        groupPanelOptions.add(checkBoxBackDoors);
        //groupPanelOptions

        //busPanel
        int width = 400;
        int height = 200;
        BusPanel busPanel = new BusPanel();
        busPanel.setBounds(180, 10, width, height);
        busPanel.setBorder(BorderFactory.createBevelBorder(1));
        busPanel.setTransferHandler(new TransferHandler("text"));
        busPanel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                String data;

                try {
                    if (t.getTransferData(DataFlavor.stringFlavor) instanceof IDrawingDoors) {
                        if (bus != null && bus.getClass() == BusWithGarmoshka.class) {
                            ((BusWithGarmoshka) bus).setDrawingDoors((IDrawingDoors) t.getTransferData(DataFlavor.stringFlavor));
                            busPanel.repaint();
                        }
                        return true;
                    } else {
                        data = (String) t.getTransferData(DataFlavor.stringFlavor);
                    }
                } catch (Exception e) {
                    return false;
                }
                switch (data) {
                    case "Обычный автобус":
                        bus = new Bus((int) maxSpeedSpinner.getValue(), (int) weightSpinner.getValue(), Color.WHITE);
                        bus.setPosition(30, 30, 150, 60);
                        break;
                    case "Автобус с гармошкой":
                        bus = new BusWithGarmoshka((int) maxSpeedSpinner.getValue(), (int) weightSpinner.getValue(), Color.WHITE, Color.BLACK, checkBoxBackDoors.isSelected(), checkBoxGarmoshka.isSelected(), 3, 1);
                        bus.setPosition(30, 30, 320, 60);
                        break;
                    default:
                        return false;
                }
                busPanel.setTransport(bus);
                busPanel.repaint();
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.isDataFlavorSupported(DataFlavor.stringFlavor) || info.getTransferable().getTransferData(DataFlavor.stringFlavor) instanceof IDrawingDoors;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        frame.getContentPane().add(busPanel);
        //busPanel

        //groupPanelDoors
        JPanel groupPanelDoors = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelDoors.setBorder(BorderFactory.createTitledBorder("Тип дверей"));
        groupPanelDoors.setBounds(busPanel.getX() + busPanel.getWidth() + 10, 10, 170, 150);
        frame.getContentPane().add(groupPanelDoors);

        setDoorLabels(groupPanelDoors, "Обычные двери", 1);
        setDoorLabels(groupPanelDoors, "Овальные двери", 2);
        setDoorLabels(groupPanelDoors, "Двойные двери", 3);
        //groupPanelDoors

        //groupPanelColors
        JPanel groupPanelColors = new JPanel(new GridLayout(5, 2, 0, 2));
        groupPanelColors.setBorder(BorderFactory.createTitledBorder("Цвета"));
        groupPanelColors.setBounds(180, height + 10, 400, 200);
        frame.getContentPane().add(groupPanelColors);

        JLabel mainColorLabel = new JLabel("Основной цвет");
        mainColorLabel.setBorder(BorderFactory.createBevelBorder(0));
        mainColorLabel.setBounds(10, 10, 280, 50);
        mainColorLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                Color data;
                try {
                    data = (Color) t.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return false;
                }
                if (bus != null) {
                    bus.mainColor = data;
                    busPanel.repaint();
                }
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.getTransferable().getTransferData(DataFlavor.stringFlavor).getClass() == Color.class;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }
            }
        });
        groupPanelColors.add(mainColorLabel);

        JLabel dopColorLabel = new JLabel("Дополнительный цвет");
        dopColorLabel.setBorder(BorderFactory.createBevelBorder(0));
        dopColorLabel.setBounds(10, 70, 280, 50);
        dopColorLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                Color data;
                try {
                    data = (Color) t.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return false;
                }
                if (bus != null) {
                    if (bus.getClass() == BusWithGarmoshka.class) {
                        ((BusWithGarmoshka) bus).setDopColor(data);
                        busPanel.repaint();
                    }
                }
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.getTransferable().getTransferData(DataFlavor.stringFlavor).getClass() == Color.class;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }
            }
        });
        groupPanelColors.add(dopColorLabel);

        setColors(Color.BLUE, groupPanelColors);
        setColors(Color.GREEN, groupPanelColors);
        setColors(Color.RED, groupPanelColors);
        setColors(Color.YELLOW, groupPanelColors);
        setColors(Color.PINK, groupPanelColors);
        setColors(Color.ORANGE, groupPanelColors);
        setColors(Color.CYAN, groupPanelColors);
        setColors(Color.WHITE, groupPanelColors);
        //groupPanelColors

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            if (bus == null) {
                JOptionPane.showMessageDialog(frame, "Сначала создайте автобус!", "Добавление автобуса", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (eventAddBus != null) {
                eventAddBus.BusDelegate(bus);
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        addButton.setBounds(groupPanelOptions.getX(), groupPanelOptions.getY() + groupPanelOptions.getHeight() + 10, 100, 50);
        frame.add(addButton);

        JButton cancelButton = new JButton("Отменить");
        cancelButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        cancelButton.setBounds(groupPanelOptions.getX(), addButton.getY() + addButton.getHeight() + 10, 100, 50);
        frame.add(cancelButton);
    }

    private void setDoorLabels(JPanel parent, String name, int type) {
        IDrawingDoors doors;
        switch (type) {
            case 2:
                doors = new DrawingOvalDoors();
                break;
            case 3:
                doors = new DrawingDoubleOvalDoors();
                break;
            default:
                doors = new DrawingStandardDoors();
        }
        JLabel DNDLabel = new JLabel(name);
        DNDLabel.setBorder(BorderFactory.createBevelBorder(0));
        DNDLabel.setBounds(10, 70, 280, 50);
        DNDLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) {
                        return doors;
                    }
                };
            }
        });
        DNDLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        parent.add(DNDLabel);
    }

    void setColors(Color color, JPanel groupPanelColors) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) {
                        return panel.getBackground();
                    }
                };
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        panel.setBounds(10, 10, 100, 100);
        groupPanelColors.add(panel);
    }

    void setBusLabels(JPanel parent, String name) {
        JLabel DNDLabel = new JLabel(name);
        DNDLabel.setBorder(BorderFactory.createBevelBorder(0));
        DNDLabel.setBounds(10, 70, 280, 50);
        DNDLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new StringSelection(((JLabel) c).getText());
            }
        });
        DNDLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        parent.add(DNDLabel);
    }

    public void addEvent(IBusDelegate ev) {
        eventAddBus = ev;
    }

    public void mouseAction(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e)) {
            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }
}
