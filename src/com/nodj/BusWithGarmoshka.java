package com.nodj;

import java.awt.*;

public class BusWithGarmoshka {
    private int _startPosX;
    private int _startPosY;
    private int _pictureWidth;
    private int _pictureHeight;
    private final int carWidth = 320;
    private final int carHeight = 60;

    private int maxSpeed;
    private float weight;
    private Color mainColor;
    private Color dopColor;
    private boolean backDoors;
    private boolean garmoshka;
    DrawingDoors drawingDoors = new DrawingDoors();

    BusWithGarmoshka(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean backDoors, boolean garmoshka) {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
        this.dopColor = dopColor;
        this.backDoors = backDoors;
        this.garmoshka = garmoshka;
    }

    void setPosition(int x, int y, int width, int height) {
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }

    void moveTransport(Direction direction) {
        float step = maxSpeed * 100 / weight;
        switch (direction) {
            // вправо
            case Right:
                if (_startPosX + step < _pictureWidth - carWidth) {
                    _startPosX += step;
                }
                break;
            //влево
            case Left:
                if (_startPosX - step > 0) {
                    _startPosX -= step;
                }
                break;
            //вверх
            case Up:
                if (_startPosY - step > 0) {
                    _startPosY -= step;
                }
                break;
            //вниз
            case Down:
                if (_startPosY + step < _pictureHeight - carHeight) {
                    _startPosY += step;
                }
                break;
        }
    }

    void drawTransport(Graphics g) {
        g.setColor(mainColor); // cusov
        g.fillRect(_startPosX, _startPosY, 150, 50);
        g.setColor(dopColor); // Door
        g.fillRect(_startPosX, _startPosY + 5, 10, 25);
        g.fillRect(_startPosX, _startPosY + 40, 5, 5);
        g.setColor(Color.BLACK); // wheels
        g.fillOval(_startPosX + 5, _startPosY + 40, 20, 20);
        g.fillOval(_startPosX + 120, _startPosY + 40, 20, 20);
        if (garmoshka) {

            g.fillRect(_startPosX + 150, _startPosY, 20, 50);

            g.setColor(mainColor);
            g.fillRect(_startPosX + 170, _startPosY, 150, 50);

            g.setColor(Color.BLACK);
            g.fillOval(_startPosX + 175, _startPosY + 40, 20, 20);
            g.fillOval(_startPosX + 290, _startPosY + 40, 20, 20);
        }
        drawingDoors.drawDoors(g, dopColor, _startPosX, _startPosY, backDoors, garmoshka);
    }
}
