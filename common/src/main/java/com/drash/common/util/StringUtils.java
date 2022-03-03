package com.drash.common.util;

import com.drash.common.internal.ToBooleanBiFunction;

import java.lang.reflect.Array;
import java.util.Locale;
import java.util.Objects;

public final class StringUtils {

    //Private constructor to prevent external instantiation
    private StringUtils() {
        //Intentionally blank
    }

    public static String defaultString(final String value, final String defaultString) {
        return isBlank(value) ? defaultString : value;
    }

    public static boolean isBlank(final String string) {
        if (Objects.isNull(string)) {
            return true;
        }
        return string.trim().length() == 0;
    }

    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean equals(final String str1, final String str2) {
        if (str1 != null && str2 != null) {
            return str1.length() == str2.length() && str1.equals(str2);
        }
        return false;
    }

    public static boolean equalsIgnoreCase(final String str1, final String str2) {
        if (isBlank(str1) || isBlank(str2)) {
            throw new IllegalArgumentException("Arguments must not be blank.");
        }
        return str1.toLowerCase(Locale.ENGLISH)
                .equals(str2.toLowerCase(Locale.ENGLISH));
    }

    public static String replaceFirst(final String text, final String regex, final String replacement) {
        if (Objects.isNull(text) || Objects.isNull(regex) || Objects.isNull(replacement)) {
            return text;
        }
        return text.replaceFirst(regex, replacement);
    }

    public static boolean contains(final String cs, final String search) {
        if (Objects.isNull(cs) || (isBlank(cs) && isNotBlank(search))) {
            return false;
        }
        return cs.contains(search);
    }

    public static boolean containsAny(final String cs, final String... searchChars) {
        return containsAny(String::contains, cs, searchChars);
    }

    private static boolean containsAny(final ToBooleanBiFunction<String, String> test,
                                       final String cs, final String... searchCharSequences) {
        final var isSearchCharSeqEmpty = searchCharSequences == null || Array.getLength(searchCharSequences) == 0;
        if (isEmpty(cs) || isSearchCharSeqEmpty) {
            return false;
        }

        for (final String searchCharSequence : searchCharSequences) {
            if (test.applyAsBoolean(cs, searchCharSequence)) {
                return true;
            }
        }
        return false;
    }
}
