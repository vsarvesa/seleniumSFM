package tests;

/*Write a Java Program to count the number of words present in a string using HashMap.
String "You cannot count on me, As I count on you"
*/
import java.util.*;

import static java.lang.Integer.parseInt;

class maintest{
    public static void main(String args[]){
        String verb= "You cannot count on me, As I count on you";
        String captiverb = verb.toUpperCase();
        int count = 1;
        Map<String,Integer> wordmap= new HashMap<>();
        for (String s : captiverb.split(" ")){
            wordmap.put(s,wordmap.getOrDefault(s,0)+1);
        }
        System.out.print(wordmap);

                int a=45;
                int b=70;
                double c=65.45;
                int add=a+b;
                var diiff=c-b;
                System.out.println(add);
                System.out.println(diiff);

    }
}

