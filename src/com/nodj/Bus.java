package com.nodj;

import java.awt.*;

public class Bus extends Vehicle {
    protected int busWidth = 150;
    protected int busHeight = 60;
    protected String separator = ";";

    public Bus(int maxSpeed, float weight, Color mainColor) {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
    }

    public Bus(String info) {
        String[] strs = info.split(separator);
        if (strs.length == 3) {
            maxSpeed = Integer.parseInt(strs[0]);
            weight = Float.parseFloat(strs[1]);
            mainColor = Color.decode(strs[2]);
        }
    }

    protected Bus(int maxSpeed, float weight, Color mainColor, int busWidth, int busHeight) {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
        this.busWidth = busWidth;
        this.busHeight = busHeight;
    }

    @Override
    public void moveTransport(Direction direction) {
        float step = maxSpeed * 100 / weight;
        switch (direction) {
            // вправо
            case Right:
                if (_startPosX + step < _pictureWidth - busWidth) {
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
                if (_startPosY + step < _pictureHeight - busHeight) {
                    _startPosY += step;
                }
                break;
        }
    }

    @Override
    public void drawTransport(Graphics g) {
        g.setColor(mainColor); // cusov
        g.fillRect(_startPosX, _startPosY, 150, 50);
        g.setColor(Color.yellow); // door
        g.fillRect(_startPosX, _startPosY + 5, 10, 25);
        g.fillRect(_startPosX, _startPosY + 40, 5, 5);
        g.setColor(Color.BLACK); // wheels
        g.fillOval(_startPosX + 5, _startPosY + 40, 20, 20);
        g.fillOval(_startPosX + 120, _startPosY + 40, 20, 20);
    }

    @Override
    public String toString() {
        return maxSpeed + separator
                + weight + separator
                + mainColor.getRGB();
    }
}
