package com.nodj;

import java.awt.*;

public class BusWithGarmoshka extends Bus implements Comparable<BusWithGarmoshka>{

    protected Color dopColor;
    protected boolean backDoors;
    protected boolean garmoshka;
    private IDrawingDoors drawingDoors;
    private int numDoors;
    private int numType;
    static private final String separator = ";";

    BusWithGarmoshka(int maxSpeed,
                     float weight,
                     Color mainColor,
                     Color dopColor,
                     boolean backDoors,
                     boolean garmoshka,
                     int numDoors,
                     int numType) {
        super(maxSpeed, weight, mainColor);
        this.dopColor = dopColor;
        this.backDoors = backDoors;
        this.garmoshka = garmoshka;
        this.numDoors = numDoors;
        this.numType = numType;
        drawingDoors = setDrawingDoors(numType);
        drawingDoors.setConfig(numDoors);
    }

    public BusWithGarmoshka(String info) {
        super(Integer.parseInt(info.split(separator)[0]), Float.parseFloat(info.split(separator)[1]), Color.decode(info.split(separator)[2]));

        String[] strs = info.split(separator);
        if (strs.length == 8) {
            dopColor = Color.decode(strs[3]);
            backDoors = Boolean.parseBoolean(strs[4]);
            garmoshka = Boolean.parseBoolean(strs[5]);
            numDoors = Integer.parseInt(strs[6]);
            numType = Integer.parseInt(strs[7]);
            drawingDoors = setDrawingDoors(numType);
            drawingDoors.setConfig(numDoors);

            props.add(dopColor);
            props.add(backDoors);
            props.add(garmoshka);
            props.add(numDoors);
            props.add(numType);
            props.add(drawingDoors.getClass().getName());
        }
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

    public void setDopColor(Color dopColor) {
        this.dopColor = dopColor;
    }

    public void setDrawingDoors(IDrawingDoors drawingDoors) {
        this.drawingDoors = drawingDoors;
    }

    @Override
    public String toString() {
        return super.toString() + separator
                + dopColor.getRGB() + separator
                + backDoors + separator
                + garmoshka + separator
                + numDoors + separator
                + numType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj.getClass() == BusWithGarmoshka.class)) {
            return false;
        } else {
            return equals((BusWithGarmoshka) obj);
        }
    }

    public boolean equals(BusWithGarmoshka other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != BusWithGarmoshka.class) {
            return false;
        }
        if (maxSpeed != other.maxSpeed) {
            return false;
        }
        if (weight != other.weight) {
            return false;
        }
        if (mainColor.getRGB() != other.mainColor.getRGB()) {
            return false;
        }
        if (dopColor.getRGB() != other.dopColor.getRGB()) {
            return false;
        }
        if (backDoors != other.backDoors) {
            return false;
        }
        if (garmoshka != other.garmoshka) {
            return false;
        }
        if (numDoors != other.numDoors) {
            return false;
        }
        return drawingDoors.getClass() == other.drawingDoors.getClass();
    }

    public int compareTo(BusWithGarmoshka other) {
        if (maxSpeed != other.maxSpeed)
        {
            return Integer.compare(maxSpeed, other.maxSpeed);
        }
        if (weight != other.weight)
        {
            return Float.compare(weight, other.weight);
        }
        if (mainColor.getRGB() != other.mainColor.getRGB())
        {
            return Integer.compare(mainColor.getRGB(), other.mainColor.getRGB());
        }
        if (dopColor.getRGB() != other.dopColor.getRGB())
        {
            return Integer.compare(dopColor.getRGB(), other.dopColor.getRGB());
        }
        if (backDoors != other.backDoors)
        {
            return Boolean.compare(backDoors, other.backDoors);
        }
        if (garmoshka != other.garmoshka)
        {
            return Boolean.compare(garmoshka, other.garmoshka);
        }
        return 0;
    }
}
