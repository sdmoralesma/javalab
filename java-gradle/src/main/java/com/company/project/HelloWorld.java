package com.company.project;

public class HelloWorld {

    public static void main(String[] args) {
        HelloWorld greeter = new HelloWorld();
        System.out.println(greeter.sayHello());
    }

    public String sayHello() {
        return "hello world!";
    }
}
