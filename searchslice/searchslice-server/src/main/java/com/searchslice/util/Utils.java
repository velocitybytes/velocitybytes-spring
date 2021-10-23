package com.searchslice.util;

import java.util.List;

public class Utils {

    public static boolean containsValue(List<?> list, String value) {
        return list.stream().anyMatch(item -> item.equals(value));
    }
}
