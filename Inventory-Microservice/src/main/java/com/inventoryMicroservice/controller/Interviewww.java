package com.inventoryMicroservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Interviewww {

    public static List<String> getMatchingStrings(String[] arr, String s1) {
        return Arrays.stream(arr)
                .filter(str -> str.contains(s1))
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {
        String[] arr = { "ABCD", "DBCA", "XYZ", "CBAD" };
        String s1 = "BA";
        List<String> matchingStrings = getMatchingStrings(arr, s1);
        System.out.println(matchingStrings);

        String s = "Today is the first day of my work day";
        Map<String, Long> wordFrequency = Arrays.stream(s.split(" "))
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()));
        System.out.println(wordFrequency);

    }
}
