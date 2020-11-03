package com.nodj;

import javax.swing.*;
import java.awt.*;

public class StationPanel extends JPanel {
    private Station<Vehicle, IDrawingDoors> station;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (station != null)
            station.Draw(g);
    }

    public void setStation(Station<Vehicle, IDrawingDoors> station) {
        this.station = station;
    }

}
