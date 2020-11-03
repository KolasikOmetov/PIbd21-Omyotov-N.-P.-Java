package com.nodj;

import java.util.HashMap;

public class StationCollection {
    private final HashMap<String, Station<Vehicle, IDrawingDoors>> stationStages;

    public String[] keys() {
        return stationStages.keySet().toArray(new String[stationStages.keySet().size()]);
    }

    private final int pictureWidth;

    private final int pictureHeight;

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
}
