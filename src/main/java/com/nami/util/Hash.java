package com.nami.util;

public class Hash {

    public static long hash64(String str) {
        long h = 1125899906842597L;

        int len = str.length();
        for (int i = 0; i < len; i++)
            h = 31 * h + str.charAt(i);

        return h;
    }

}
