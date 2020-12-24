package com.nodj;

import java.util.Comparator;

public class BusComparer implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle x, Vehicle y) {
        if (x.getClass() == y.getClass())
        {
            if(x.getClass() == Bus.class)
            {
                return comparerBus((Bus)x, (Bus)y);
            }
            else
            {
                return comparerBusWithGarmoshka((BusWithGarmoshka)x, (BusWithGarmoshka)y);
            }
        }
        else
        {
            if (x.getClass() == Bus.class)
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
    }

    private int comparerBus(Bus x, Bus y)
    {
        if (x.maxSpeed != y.maxSpeed)
        {
            return Integer.compare(x.maxSpeed, y.maxSpeed);
        }
        if (x.weight != y.weight)
        {
            return Float.compare(x.weight, y.weight);
        }
        if (x.mainColor.getRGB() != y.mainColor.getRGB())
        {
            return Integer.compare(x.mainColor.getRGB(), y.mainColor.getRGB());
        }
        return 0;
    }

    private int comparerBusWithGarmoshka(BusWithGarmoshka x, BusWithGarmoshka y)
    {
        int res = comparerBus(x, y);
        if (res != 0)
        {
            return res;
        }
        if (x.dopColor.getRGB() != y.dopColor.getRGB())
        {
            return Integer.compare(x.dopColor.getRGB(), y.dopColor.getRGB());
        }
        if (x.backDoors != y.backDoors)
        {
            return Boolean.compare(x.backDoors, y.backDoors);
        }
        if (x.garmoshka != y.garmoshka)
        {
            return Boolean.compare(x.garmoshka, y.garmoshka);
        }
        return 0;
    }
}
