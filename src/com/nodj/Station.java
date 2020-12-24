package com.nodj;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;

public class Station<T extends ITransport, D extends IDrawingDoors> implements Iterable<Vehicle> {
    private final ArrayList<T> places;
    private final int _maxCount;
    private final int pictureWidth;
    private final int pictureHeight;
    private final int _placeSizeWidth = 350;
    private final int _placeSizeHeight = 80;

    private int _currentIndex = -1;

    public Station(int picWidth, int picHeight) {
        int columns = picWidth / _placeSizeWidth;
        int rows = picHeight / _placeSizeHeight;
        _maxCount = columns * rows;
        places = new ArrayList<>();
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public boolean add(T bus) throws StationOverflowException, StationAlreadyHaveException { // +
        if (places.size() >= _maxCount) {
            throw new StationOverflowException();
        }
        for (T curBus : places){
            if(bus.equals(curBus)){
                throw new StationAlreadyHaveException();
            }
        }
        places.add(bus);
        return true;
    }

    public T remove(int index) throws StationNotFoundException { // -
        if (index < 0 || index >= places.size()) {
            throw new StationNotFoundException(index);
        }

        T bus = places.get(index);

        places.remove(index);

        return bus;
    }

    public T get(int index) {
        if (index < 0 || index > places.size() - 1) {
            return null;
        }
        return places.get(index);
    }

    public boolean equal(int numBus) { // ==
        return numBus == places.size();
    }

    public boolean nonEqual(int numBus) { // !=
        return !equal(numBus);
    }

    public void Draw(Graphics g) {
        DrawMarking(g);
        for (int i = 0; i < places.size(); ++i) {
            places.get(i).setPosition(5 + i / 5 * _placeSizeWidth + 5, i % 5 * _placeSizeHeight + 15, pictureWidth, pictureHeight);
            places.get(i).drawTransport(g);
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

    public T getNext(int index) {
        if (index < 0 || index >= places.size()) {
            return null;
        }
        return places.get(index);
    }

    void clear() {
        places.clear();
    }

    public void sort() {
        places.sort((Comparator<T>) new BusComparer());
    }

    public void reset() {
        _currentIndex = -1;
    }

    @Override
    public Iterator<Vehicle> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Vehicle> action) {
        if (action == null)
            throw new NullPointerException();
        if (places.size() > 0) {
            int mc = _currentIndex;
            for (T e : places) {
                action.accept((Vehicle) e);
            }
            if (_currentIndex != mc)
                throw new ConcurrentModificationException();
        }
        reset();
    }

}
