package com.sygic.sdk.api.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    public static <E> ArrayList<E> asArrayList(E[] array) {
        if (array != null) {
            return new ArrayList(Arrays.asList(array));
        }
        return new ArrayList();
    }
}
