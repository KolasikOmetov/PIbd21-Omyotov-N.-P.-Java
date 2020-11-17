package com.nodj;

import java.awt.*;
import java.io.Serializable;

public interface IDrawingDoors extends Serializable {
    void setConfig(int config);
    void drawDoors(Graphics g, Color dopColor, int _startPosX, int _startPosY, boolean backDoors, boolean garmoshka);
}
