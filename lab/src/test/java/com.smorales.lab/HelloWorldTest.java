package com.smorales.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HelloWorldTest {

    private HelloWorld helloWorld;

    @Before
    public void setUp() {
        helloWorld = new HelloWorld();
    }

    @Test
    public void testJodaTime() {
        Assert.assertEquals(3, helloWorld.jodaTime());
    }

    @Test
    public void testGoogleGuava() {
        Assert.assertEquals("Guava is working: Harry; Ron; Hermione", helloWorld.googleGuava());
    }

    @Test
    public void testApacheCommonsLang() {
        Assert.assertEquals("The quick brown fox jumped over the lazy dog.", helloWorld.apacheCommonsLang());
    }
}
