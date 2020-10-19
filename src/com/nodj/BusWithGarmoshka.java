package com.nodj;

import java.awt.*;

public class BusWithGarmoshka extends Bus {

    private Color dopColor;
    private boolean backDoors;
    private boolean garmoshka;
    private IDrawingDoors drawingDoors;

    BusWithGarmoshka(int maxSpeed,
                     float weight,
                     Color mainColor,
                     Color dopColor,
                     int busWidth,
                     int busHeight,
                     boolean backDoors,
                     boolean garmoshka,
                     int numDoors,
                     int numType) {
        super(maxSpeed, weight, mainColor, busWidth, busHeight);
        this.dopColor = dopColor;
        this.backDoors = backDoors;
        this.garmoshka = garmoshka;
        this.drawingDoors = setDrawingDoors(numType);
        drawingDoors.setConfig(numDoors);
    }

    private IDrawingDoors setDrawingDoors(int numType) {
        switch (numType) {
            case 2:
                return new DrawingOvalDoors();
            case 3:
                return new DrawingDoubleOvalDoors();
            default:
                return new DrawingStandardDoors();
        }
    }

    public void setNumDoors(int numDoors) {
        drawingDoors.setConfig(numDoors);
    }

    @Override
    public void drawTransport(Graphics g) {
        super.drawTransport(g);
        if (garmoshka) {
            g.setColor(mainColor);
            g.fillRect(_startPosX + 170, _startPosY, 150, 50);
            g.setColor(Color.BLACK);
            g.fillRect(_startPosX + 150, _startPosY, 20, 50);
            g.fillOval(_startPosX + 175, _startPosY + 40, 20, 20);
            g.fillOval(_startPosX + 290, _startPosY + 40, 20, 20);
        }
        drawingDoors.drawDoors(g, dopColor, _startPosX, _startPosY, backDoors, garmoshka);
    }
}
