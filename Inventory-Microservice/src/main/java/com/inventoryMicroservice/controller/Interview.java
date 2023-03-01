package com.inventoryMicroservice.controller;

import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Interview {

    public static void main(String[] args) {
        //**//word count
        String str = "this this is a good good boy";
        List<String> list = Arrays.asList(str.split(" "));
        Map<String, Long> map = list.stream().collect(groupingBy(Function.identity(), counting()));
        map.entrySet().forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));

        //**//remove duplicate string
        String str1 = "raja raja raja";
        //approach 1
        String[] s = str1.split("[\\s]");
        List<String> collect = Stream.of(s).distinct().collect(Collectors.toList());
        System.out.println(collect);

        //approach 2
        List<String> list1 = Arrays.asList(str1.split("[\\s]"));
        list1.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);

        //**//reverse a sentense
        String input = "i am a good boy";
        List<String> list2 = Arrays.asList(input.split(" "));
        Collections.reverse(list2);
        System.out.println(list2.stream().collect(Collectors.joining(" ")));

        //**//word count
        String s1 = "Today is the first day of my work day";
        Stream<String> str3 = Arrays.stream(s1.split(" "));
        Map<String, Long> map23 = str3.distinct().collect(groupingBy((String str4) -> str4, counting()));
        System.out.println(map23);

        //*//remove duplicate characters in string
        String s5 = "asdedffdery";
        Stream<String> str5 = Arrays.stream(s5.split(""));
        str5.distinct().collect(Collectors.toList()).forEach(System.out::print);

        //**//Sort an array
        System.out.println(" ");
        Integer[] i = {1,2,8,3,10,4,9,5};
        Arrays.stream(i).sorted().collect(Collectors.toList()).forEach(System.out::print);

        //*//char occurance in string
        System.out.println(" ");
        String strn = "ggdhhdtteggrg";
        Map<String, Long> m = Arrays.stream(strn.split("")).collect(groupingBy(Function.identity(),counting()));
        System.out.println(m);

    //**// list of string occurance

        List<String> l = Arrays.asList("abc","abc");
        Map<String, Long> collect1 = l.stream().collect(groupingBy(Function.identity(), counting()));
        System.out.println(collect1);

       Integer[] integers = {1,2,3,4,5,6};
       List<Integer> list3 = Arrays.asList(integers);
       list3.stream().sorted(Collections.reverseOrder()).forEach(System.out::println);

        int arry[] = {26, 98, 1918, 2825};
        int max = Arrays.stream(arry).max().getAsInt();
        System.out.print("Largest Element is: " + max);

    }
}
