package com.smorales.javalab.workspaceprocessor.control;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Base62Test {

    private Base62 sut;

    @Before
    public void setUp() {
        sut = new Base62();
    }

    @Test
    public void testCharList() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            sb.append(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            sb.append(c);
        }
        for (int i = 0; i <= 9; i++) {
            sb.append(i);
        }

        assertThat(Base62.ALPHABET).isEqualTo(sb.toString());
    }

    @Test
    public void testStringFromInt() throws Exception {
        int n = 0;
        String str = "6JaY2";
        char[] chars = str.toCharArray();
        n += Base62.ALPHABET.indexOf(chars[0]) * (int) Math.pow(62, 4);
        n += Base62.ALPHABET.indexOf(chars[1]) * (int) Math.pow(62, 3);
        n += Base62.ALPHABET.indexOf(chars[2]) * (int) Math.pow(62, 2);
        n += Base62.ALPHABET.indexOf(chars[3]) * (int) Math.pow(62, 1);
        n += Base62.ALPHABET.indexOf(chars[4]) * (int) Math.pow(62, 0);

        assertThat(sut.fromBase10(n)).isEqualTo(str);
    }

    @Test
    public void testIntegerFromString() throws Exception {
        assertThat(sut.toBase10("cb")).isEqualTo(125);
    }

    @Test
    public void shoulConvertToBase62WithOffset() throws Exception {
        int toConvert = 1;
        int offset = 1000000;
        assertThat(sut.fromBase10WithOffset(toConvert, offset)).isEqualTo("emjd");
    }

    @Test
    public void shoulConvertToBase10WithOffset() throws Exception {
        String toConvert = "emjd";
        int offset = 1000000;
        assertThat(sut.toBase10WithOffset(toConvert, offset)).isEqualTo(1);
    }
}