package org.deslre.utils;


public class StringUtil {

    public static boolean isEmpty(final CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(final CharSequence str) {
        final int strLen = length(str);
        if (strLen == 0)
            return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence str) {
        return !isBlank(str);
    }

    public static int length(final CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static String lowerCase(final String str) {
        if (str == null)
            return null;
        return str.toLowerCase();
    }

}
