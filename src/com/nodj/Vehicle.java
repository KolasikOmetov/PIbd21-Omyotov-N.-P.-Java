package com.nodj;

import java.awt.*;

public abstract class Vehicle implements ITransport {
    protected int _startPosX;

    protected int _startPosY;

    protected int _pictureWidth;

    protected int _pictureHeight;

    public int maxSpeed;
    public float weight;
    public Color mainColor;

    public void setPosition(int x, int y, int width, int height)
    {
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }

    public abstract void drawTransport(Graphics g);
    public abstract void moveTransport(Direction direction);
}
