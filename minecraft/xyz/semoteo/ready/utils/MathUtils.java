package xyz.semoteo.ready.utils;

import java.math.RoundingMode;

import com.ibm.icu.math.BigDecimal;


public final class MathUtils
{
    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places);
        return bd.doubleValue();
    }
}
