package com.example.ivan.weatherapp.utils;

/**
 * @author Roodie
 */

import android.support.annotation.Nullable;

public final class TextUtils {

    private TextUtils() {
    }

    public static boolean isEmpty(@Nullable CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean equals(@Nullable CharSequence a, @Nullable CharSequence b) {
        if (a == b) {
            return true;
        }

        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static String repeat(String text, int count) {
        StringBuilder finalText = new StringBuilder();
        for (int i = 0; i < count; i++)
            finalText.append(text);
        return finalText.toString();
    }

}
