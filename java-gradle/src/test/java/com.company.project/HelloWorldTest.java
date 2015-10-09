package com.company.project;

import org.junit.*;

public class HelloWorldTest {

    private HelloWorld sut;

    @Before
    public void setUp() {
        sut = new HelloWorld();
    }

    @Test
    public void testHello() {
        Assert.assertEquals("hello world!", sut.sayHello());
    }
}
