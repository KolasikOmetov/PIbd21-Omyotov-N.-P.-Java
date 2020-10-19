package com.nodj;

import java.awt.*;

public interface IDrawingDoors {
    void setConfig(int config);
    void drawDoors(Graphics g, Color dopColor, int _startPosX, int _startPosY, boolean backDoors, boolean garmoshka);
}
