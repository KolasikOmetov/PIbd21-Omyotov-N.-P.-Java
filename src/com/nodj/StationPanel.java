package com.nodj;

import javax.swing.*;
import java.awt.*;

public class StationPanel extends JPanel {
    private final Station<ITransport, IDrawingDoors> station;

    StationPanel(Station<ITransport, IDrawingDoors> station){
        this.station = station;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        station.Draw(g);
    }
}
