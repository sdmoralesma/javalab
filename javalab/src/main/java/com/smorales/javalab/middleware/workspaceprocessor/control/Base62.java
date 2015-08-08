package com.smorales.javalab.middleware.workspaceprocessor.control;

/**
 * Found on: https://gist.github.com/jdcrensh/4670128
 */
public class Base62 {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int BASE = ALPHABET.length();

    public String fromBase10WithOffset(int i, int offset) {
        return fromBase10(i + offset);
    }

    public String fromBase10(int i) {
        StringBuilder sb = new StringBuilder("");
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    private int fromBase10(int i, final StringBuilder sb) {
        int rem = i % BASE;
        sb.append(ALPHABET.charAt(rem));
        return i / BASE;
    }

    public int toBase10WithOffset(String str, int offsetBase10) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray()) - offsetBase10;
    }

    public int toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private int toBase10(char[] chars) {
        int n = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private int toBase10(int n, int pow) {
        return n * (int) Math.pow(BASE, pow);
    }
}