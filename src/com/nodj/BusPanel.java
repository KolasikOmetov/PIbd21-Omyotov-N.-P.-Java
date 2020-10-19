package com.nodj;

import javax.swing.*;
import java.awt.*;

public class BusPanel extends JPanel {
    private ITransport transport;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (transport != null)
            transport.drawTransport(g);
    }

    public void setTransport(ITransport transport) {
        this.transport = transport;
    }
}
