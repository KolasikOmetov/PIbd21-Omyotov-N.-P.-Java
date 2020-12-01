package com.nodj;

import java.io.*;
import java.util.HashMap;

public class StationCollection {
    private final HashMap<String, Station<Vehicle, IDrawingDoors>> stationStages;

    public String[] keys() {
        return stationStages.keySet().toArray(new String[stationStages.keySet().size()]);
    }

    private final int pictureWidth;

    private final int pictureHeight;

    private final String separator = ":";

    public StationCollection(int pictureWidth, int pictureHeight) {
        stationStages = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void addStation(String name) {
        if (stationStages.containsKey(name)) {
            return;
        }

        stationStages.put(name, new Station<>(pictureWidth, pictureHeight));
    }

    public void delStation(String name) {
        stationStages.remove(name);
    }

    public Station<Vehicle, IDrawingDoors> get(String ind) {
        if (!stationStages.containsKey(ind)) {
            return null;
        }
        return stationStages.get(ind);
    }

    public Vehicle get(String ind, int index) {
        if (!stationStages.containsKey(ind)) {
            return null;
        }

        return stationStages.get(ind).get(index);
    }

    public boolean SaveData(String filename, String selectedName) {
        if (!stationStages.containsKey(selectedName)) {
            return false;
        }
        try {
            File dataFile = new File(filename);
            if (dataFile.exists()) {
                if (!dataFile.delete())
                    return false;
            }
            if (!dataFile.createNewFile()) {
                return false;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
            bw.write("Station" + separator + selectedName);
            bw.newLine();
            ITransport bus;
            for (int i = 0; (bus = stationStages.get(selectedName).getNext(i)) != null; i++) {
                if (bus.getClass() == Bus.class) {
                    bw.write("Bus" + separator);
                }
                if (bus.getClass() == BusWithGarmoshka.class) {
                    bw.write("BusWithGarmoshka" + separator);
                }
                //Записываемые параметры
                bw.write(bus.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean SaveAllData(String filename) {
        try {
            File dataFile = new File(filename);
            if (dataFile.exists()) {
                if (!dataFile.delete())
                    return false;
            }
            if (!dataFile.createNewFile()) {
                return false;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
            bw.write("StationCollection");
            bw.newLine();
            stationStages.forEach((k, v) -> {
                try {
                    bw.write("Station" + separator + k);
                    bw.newLine();
                    ITransport bus;
                    for (int i = 0; (bus = v.getNext(i)) != null; i++) {
                        if (bus.getClass() == Bus.class) {
                            bw.write("Bus" + separator);
                        }
                        if (bus.getClass() == BusWithGarmoshka.class) {
                            bw.write("BusWithGarmoshka" + separator);
                        }
                        //Записываемые параметры
                        bw.write(bus.toString());
                        bw.newLine();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean LoadData(String filename) {
        try {
            File dataFile = new File(filename);
            if (!dataFile.exists()) {
                return false;
            }
            boolean head = true;
            String line;
            Vehicle bus = null;
            String key = "";
            BufferedReader br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {
                line = line.replace("\r", "");
                if (head) {
                    if (line.contains("Station")) {
                        //очищаем записи
                        key = line.split(separator)[1];
                        if (stationStages.containsKey(key)) {
                            stationStages.get(key).clear();
                        } else {
                            stationStages.put(key, new Station<>(pictureWidth, pictureHeight));
                        }
                        head = false;
                    } else {
                        //если нет такой записи, то это не те данные
                        return false;
                    }
                } else {
                    if (line.equals("")) {
                        continue;
                    }
                    if (line.split(separator)[0].equals("Bus")) {
                        bus = new Bus(line.split(separator)[1]);
                    } else if (line.split(separator)[0].equals("BusWithGarmoshka")) {
                        bus = new BusWithGarmoshka(line.split(separator)[1]);
                    }
                    boolean result = stationStages.get(key).add(bus);
                    if (!result) {
                        return false;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean LoadAllData(String filename) {
        try {
            File dataFile = new File(filename);
            if (!dataFile.exists()) {
                return false;
            }
            boolean head = true;
            String line;
            Vehicle bus = null;
            String key = "";
            BufferedReader br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {
                line = line.replace("\r", "");
                if (head) {
                    if (line.contains("StationCollection")) {
                        //очищаем записи
                        stationStages.clear();
                        head = false;
                    } else {
                        //если нет такой записи, то это не те данные
                        return false;
                    }
                } else {
                    if (line.contains("Station")) {//начинаем новую парковку
                        key = line.split(separator)[1];
                        stationStages.put(key, new Station<>(pictureWidth, pictureHeight));
                        continue;
                    }
                    if (line.equals("")) {
                        continue;
                    }
                    if (line.split(separator)[0].equals("Bus")) {
                        bus = new Bus(line.split(separator)[1]);
                    } else if (line.split(separator)[0].equals("BusWithGarmoshka")) {
                        bus = new BusWithGarmoshka(line.split(separator)[1]);
                    }
                    boolean result = stationStages.get(key).add(bus);
                    if (!result) {
                        return false;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
