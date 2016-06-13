package com.npc.lottery.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.CharEncoding;

public class Base64StrUtils {

    public static byte[] getBytesUtf8(String string) {
        return Base64StrUtils.getBytesUnchecked(string, CharEncoding.UTF_8);
    }

    public static byte[] getBytesUnchecked(String string, String charsetName) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw Base64StrUtils.newIllegalStateException(charsetName, e);
        }
    }

    private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    public static String newString(byte[] bytes, String charsetName) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw Base64StrUtils.newIllegalStateException(charsetName, e);
        }
    }

    public static String newStringUtf8(byte[] bytes) {
        return Base64StrUtils.newString(bytes, CharEncoding.UTF_8);
    }

}
