package com.smorales.lab;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class HelloWorld {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        System.out.println("JodaTime: " + helloWorld.jodaTime());
        System.out.println("Google Guava: " + helloWorld.googleGuava());
        System.out.println("Apache CommonsLang: " + helloWorld.apacheCommonsLang());
    }

    String apacheCommonsLang() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("animal", "quick brown fox");
        valuesMap.put("target", "lazy dog");
        String templateString = "The ${animal} jumped over the ${target}.";
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(templateString);
    }

    String googleGuava() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        return joiner.join("Guava is working: Harry", null, "Ron", "Hermione");
    }

    int jodaTime() {
        DateTime dt = new DateTime(2005, 3, 26, 12, 0, 0, 0);
        return dt.getMonthOfYear();
    }
}