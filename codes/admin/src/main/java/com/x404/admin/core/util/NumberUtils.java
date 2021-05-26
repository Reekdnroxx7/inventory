package com.x404.admin.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
    public static DecimalFormat format = new DecimalFormat("0.##");

    public static String toFixed2(Number n) {
        if (n instanceof Double || n instanceof Float || n instanceof BigDecimal
                ) {
            return format.format(n);
        }
        return String.valueOf(n);
    }

    public static double toFixed(double v, int length) {
        return 0;
    }
}
