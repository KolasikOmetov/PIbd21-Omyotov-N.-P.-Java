package com.nodj;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.management.OperationsException;
import javax.naming.NameNotFoundException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;

import static org.slf4j.LoggerFactory.getLogger;

public class FormStation {
    private static final Logger logger = getLogger(FormStation.class);
    private final Marker fatal = MarkerFactory.getMarker("FATAL");
    public JFrame frame;
    private StationCollection stationCollection;
    private JList<String> listOfStations;
    private final StationPanel panel = new StationPanel();
    private final DefaultListModel<String> listStationModel = new DefaultListModel<>();
    private final LinkedList<Vehicle> leavingBuses = new LinkedList<>();

    /**
     * Launch the application.
     */
    FormStation() {
        initialize();
    }

    private void initialize() {
        int width = 700;
        int height = 400;
        stationCollection = new StationCollection(width, height);
        frame = new JFrame();
        frame.setBounds(100, 100, 1300, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Автовокзал");

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        frame.setJMenuBar(menuBar);

        panel.setBounds(10, 11, width, height);
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED,
                null, null, null, null));
        frame.getContentPane().add(panel);

        JLabel stationsLabel = new JLabel("Автовокзалы:");
        stationsLabel.setBounds(width + 340, 20, 100, 20);
        frame.add(stationsLabel);

        JTextField addNewStationTextField = new JTextField(2);
        addNewStationTextField.setFont(addNewStationTextField.getFont().deriveFont(20f));
        addNewStationTextField.setBounds(width + 340, 40, 200, 20);
        frame.add(addNewStationTextField);

        JButton addStationButton = new JButton("Добавить автовокзал");
        addStationButton.addActionListener(e -> {
            if (addNewStationTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Введите название автовокзала", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            stationCollection.addStation(addNewStationTextField.getText());
            reloadAllLevels();
        });
        addStationButton.setBounds(width + 340, 60, 200, 20);
        frame.add(addStationButton);

        for (String key : stationCollection.keys()) {
            listStationModel.addElement(key);
        }
        listOfStations = new JList<>(listStationModel);
        listOfStations.setLayoutOrientation(JList.VERTICAL);
        listOfStations.setBounds(width + 340, 80, 200, 80);
        listOfStations.addListSelectionListener(e -> {
            if (listOfStations.getSelectedIndex() > -1) {
                panel.setStation(stationCollection.get(listStationModel.get(listOfStations.getSelectedIndex())));
                panel.repaint();
            }
        });
        frame.add(listOfStations);

        JButton deleteStationButton = new JButton("Удалить автовокзал");
        deleteStationButton.addActionListener(e -> {
            if (listOfStations.getSelectedIndex() > -1) {
                if (JOptionPane.showConfirmDialog(frame, "Удалить автовокзал " + listStationModel.get(listOfStations.getSelectedIndex()) + "?", "Удаление", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    stationCollection.delStation(listStationModel.get(listOfStations.getSelectedIndex()));
                    reloadAllLevels();
                }
            }
        });
        deleteStationButton.setBounds(width + 340, 160, 200, 20);
        frame.add(deleteStationButton);

        JButton showLastLeavingBusButton = new JButton("Показать последний уехавший автобус");
        showLastLeavingBusButton.addActionListener(e -> {
            if (leavingBuses.size() == 0) {
                JOptionPane.showMessageDialog(frame, "Нет уехавших автобусов");
                return;
            }
            EventQueue.invokeLater(() -> {
                FormBus window;
                try {
                    window = new FormBus(frame);
                    window.frame.setVisible(true);
                    frame.setVisible(false);
                    window.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } catch (Exception exp) {
                    exp.printStackTrace();
                    return;
                }
                window.setTransport(leavingBuses.getLast());
                leavingBuses.removeLast();
            });
        });
        showLastLeavingBusButton.setBounds(width + 340, 190, 200, 20);
        frame.add(showLastLeavingBusButton);

        JButton addBusButton = new JButton("Добавить автобус");
        addBusButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            try {
                FormBusConfig window = new FormBusConfig(frame);
                window.addEvent(this::addBus);
                window.frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));
        addBusButton.setBounds(width + 20, 20, 200, 50);
        frame.getContentPane().add(addBusButton);

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
            if (listOfStations.getSelectedIndex() > -1) {
                if (!placeTextField.getText().equals("")) {
                    int numPlace;
                    try {
                        numPlace = Integer.parseInt(placeTextField.getText());
                    } catch (Exception ex) {
                        return;
                    }

                    try {
                        Vehicle bus = stationCollection.get(listStationModel.get(listOfStations.getSelectedIndex())).remove(numPlace);
                        if (bus != null) {
                            leavingBuses.add(bus);
                        }
                        panel.repaint();
                    } catch (StationNotFoundException ex) {
                        JOptionPane.showMessageDialog(frame, ex.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
                if (stationCollection.get(listStationModel.get(listOfStations.getSelectedIndex())).equal(amountPlaces))
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
                if (listOfStations.getSelectedIndex() > -1) {
                    if (stationCollection.get(listStationModel.get(listOfStations.getSelectedIndex())).nonEqual(amountPlaces))
                        JOptionPane.showMessageDialog(frame, "Вы угадали! Свободных мест на автовокзале " + amountPlaces, "Результат", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(frame, "Вы не угадали!", "Результат", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        guessFreeButton.setBounds(20, 30, 100, 50);
        groupPanelGuess.add(guessFreeButton);
    }

    private void reloadAllLevels() {
        int index = listOfStations.getSelectedIndex();

        listOfStations.setSelectedIndex(-1);
        listStationModel.clear();
        for (int i = 0; i < stationCollection.keys().length; i++) {
            listStationModel.addElement(stationCollection.keys()[i]);
        }

        if (listStationModel.size() > 0 && (index == -1 || index >= listStationModel.size())) {
            listOfStations.setSelectedIndex(0);
        } else if (listStationModel.size() > 0 && index > -1 && index < listStationModel.size()) {
            listOfStations.setSelectedIndex(index);
        }
    }

    private void addBus(Vehicle bus) {
        if (bus != null && listOfStations.getSelectedIndex() > -1) {
            try {
                if (stationCollection.get(listStationModel.get(listOfStations.getSelectedIndex())).add(bus)) {
                    panel.repaint();
                }
            } catch (StationOverflowException ex) {
                JOptionPane.showMessageDialog(frame, "Переполнение");
                logger.warn(ex.getMessage());
            }
        }
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        JMenuItem save = new JMenuItem("Сохранить");
        file.add(save);
        JMenuItem saveAll = new JMenuItem("Сохранить всё");
        file.add(saveAll);
        JMenuItem load = new JMenuItem("Загрузить");
        file.add(load);
        JMenuItem loadAll = new JMenuItem("Загрузить всё");
        file.add(loadAll);

        saveAll.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.SAVE);
            if (filename == null) {
                return;
            }
            try {
                stationCollection.SaveAllData(filename);
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
            } catch (NameNotFoundException | OperationsException ex) {
                logger.warn(ex.toString());
                JOptionPane.showMessageDialog(frame, "При сохранении произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                logger.error(fatal, ex.toString());
                JOptionPane.showMessageDialog(frame, "При сохранении произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        save.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.SAVE);
            if (filename == null) {
                return;
            }
            if (listOfStations.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(frame, "Вы не указали автовокзал", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                stationCollection.SaveData(filename, listStationModel.get(listOfStations.getSelectedIndex()));
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
            } catch (NameNotFoundException | OperationsException ex) {
                logger.warn(ex.toString());
                JOptionPane.showMessageDialog(frame, "При сохранении произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                logger.error(fatal, ex.toString());
                JOptionPane.showMessageDialog(frame, "При сохранении произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        loadAll.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.LOAD);
            if (filename == null) {
                return;
            }
            try {
                stationCollection.LoadAllData(filename);
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                reloadAllLevels();
                if (listStationModel.size() > 0) {
                    panel.setStation(stationCollection.get(listStationModel.get(0)));
                    panel.repaint();
                }
            } catch (StationOverflowException ex) {
                JOptionPane.showMessageDialog(frame, "Переполнение");
                logger.warn(ex.getMessage());
            } catch (OperationsException | IllegalArgumentException ex) {
                logger.warn(ex.toString());
                JOptionPane.showMessageDialog(frame, "При загрузке произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                logger.error(fatal, ex.toString());
                JOptionPane.showMessageDialog(frame, "При загрузке произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        load.addActionListener(e ->
        {
            String filename = fileDialogSetup(FileDialog.LOAD);
            if (filename == null) {
                return;
            }
            try {
                stationCollection.LoadData(filename);
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                listStationModel.clear();
                for (String key : stationCollection.keys()) {
                    listStationModel.addElement(key);
                }
                if (listStationModel.size() > 0) {
                    panel.setStation(stationCollection.get(listStationModel.get(0)));
                    panel.repaint();
                }
                panel.repaint();
            } catch (StationOverflowException ex) {
                JOptionPane.showMessageDialog(frame, "Переполнение");
                logger.warn(ex.getMessage());
            } catch (OperationsException | IllegalArgumentException ex) {
                logger.warn(ex.toString());
                JOptionPane.showMessageDialog(frame, "При загрузке произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                logger.error(fatal, ex.toString());
                JOptionPane.showMessageDialog(frame, "При загрузке произошла какая-то ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return file;
    }

    private String fileDialogSetup(int type) {
        File filename;
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new OpenFileFilter(".txt"));
        int returnVal;
        if (type == 0) {
            returnVal = chooser.showOpenDialog(frame);
        } else {
            returnVal = chooser.showSaveDialog(frame);
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile();
            return filename.getAbsolutePath();
        }
        return null;
    }
}
