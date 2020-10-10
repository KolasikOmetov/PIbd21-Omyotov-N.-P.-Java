package com.nodj;

import java.awt.*;

class DrawingDoors {
    private Doors numDoors = Doors.Three;

    void setConfig(int config) {
        switch (config) {
            case 4:
                numDoors = Doors.Four;
                break;
            case 5:
                numDoors = Doors.Five;
                break;
            default:
                numDoors = Doors.Three;
        }
    }

    void drawDoors(Graphics g, Color dopColor, int _startPosX, int _startPosY, boolean backDoors, boolean garmoshka) {
        g.setColor(dopColor);
        g.fillRect(_startPosX + 25, _startPosY + 5, 25, 40);
        switch (numDoors) {
            case Five:
                if (garmoshka) {
                    g.fillRect(_startPosX + 235, _startPosY + 5, 25, 40);
                } else {
                    g.fillRect(_startPosX + 125, _startPosY + 5, 25, 40);
                }
            case Four:
                if (garmoshka) {
                    g.fillRect(_startPosX + 195, _startPosY + 5, 25, 40);
                } else {
                    g.fillRect(_startPosX + 80, _startPosY + 5, 25, 40);
                }
            case Three:
                if (garmoshka) {
                    checkBackdoors(g, _startPosX, _startPosY, backDoors);
                } else {
                    if (backDoors) {
                        g.fillRect(_startPosX + 100, _startPosY + 5, 25, 40);
                        g.fillRect(_startPosX + 60, _startPosY + 5, 25, 40);
                    } else {
                        g.fillRect(_startPosX + 50, _startPosY + 5, 25, 40);
                        g.fillRect(_startPosX + 75, _startPosY + 5, 25, 40);
                    }
                }
                break;

        }
    }

    private void drawBackdoors(Graphics g, int _startPosX, int _startPosY) {
        g.fillRect(_startPosX + 100, _startPosY + 5, 25, 40);
        g.fillRect(_startPosX + 270, _startPosY + 5, 25, 40);
    }

    private void drawNon_BackDopDoors(Graphics g, int _startPosX, int _startPosY) {
        g.fillRect(_startPosX + 50, _startPosY + 5, 25, 40);
        g.fillRect(_startPosX + 220, _startPosY + 5, 25, 40);
    }

    private void checkBackdoors(Graphics g, int _startPosX, int _startPosY, boolean backDoors) {
        if (backDoors) {
            drawBackdoors(g, _startPosX, _startPosY);
        } else {
            drawNon_BackDopDoors(g, _startPosX, _startPosY);
        }
    }

}
