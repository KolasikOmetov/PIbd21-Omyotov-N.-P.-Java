package com.nodj;

import javax.swing.*;
import java.awt.*;

public class BusPanel extends JPanel {
    private BusWithGarmoshka busWithGarmoshka;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (busWithGarmoshka != null)
            busWithGarmoshka.drawTransport(g);
    }

    public void setBusWithGarmoshka(BusWithGarmoshka busWithGarmoshka) {
        this.busWithGarmoshka = busWithGarmoshka;
    }
}
