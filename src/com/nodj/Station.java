package com.nodj;

import java.awt.*;
import java.util.ArrayList;

public class Station<T extends ITransport, D extends IDrawingDoors> {
    private ArrayList<T> places;
    private int pictureWidth;
    private int pictureHeight;
    private int rows;
    private int _placeSizeWidth = 350;
    private int _placeSizeHeight = 80;

    public Station(int picWidth, int picHeight) {
        int columns = picWidth / _placeSizeWidth;
        rows = picHeight / _placeSizeHeight;
        places = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            places.add(null);
        }
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public boolean add(T bus) { // +
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) == null) {
                bus.setPosition(i / rows * _placeSizeWidth + 15, (i - i / rows * rows) * _placeSizeHeight + 15, pictureWidth, pictureHeight);
                places.set(i, bus);
                return true;
            }
        }
        return false;
    }

    public T remove(int index) { // -
        if (index < 0 || index > places.size() - 1) {
            return null;
        }
        T bus = places.get(index);
        places.set(index, null);
        return bus;
    }

    public boolean equal(int numBus) { // ==
        int curNumBus = 0;
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) != null) {
                curNumBus++;
            }
        }
        return numBus == curNumBus;
    }

    public boolean nonEqual(int numBus) { // !=
        return !equal(numBus);
    }

    public void Draw(Graphics g) {
        DrawMarking(g);
        for (T bus : places) {
            if (bus != null) {
                bus.drawTransport(g);
            }
        }
    }

    private void DrawMarking(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < pictureWidth / _placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / _placeSizeHeight + 1; ++j) {
                g2.drawLine(i * _placeSizeWidth, j * _placeSizeHeight, i * _placeSizeWidth + _placeSizeWidth / 2, j * _placeSizeHeight);
            }
            g2.drawLine(i * _placeSizeWidth, 0, i * _placeSizeWidth, (pictureHeight / _placeSizeHeight) * _placeSizeHeight);
        }
    }
}
