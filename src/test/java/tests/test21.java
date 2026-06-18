package tests;

import java.util.*;

public class test21 {
    public static void main(String args[]) {
        int x = 12345;
        List<Integer> mid = new ArrayList<>();
        while (x > 0) {
            int y = x % 10;
            x = x / 10;
            mid.add(y);

        }
        int size = mid.size();
        System.out.println(mid.get(size / 2));

        String word = "aaabbdddc";
        Map<Character, Integer> occur = new HashMap<>();

        for (char ch : word.toCharArray()) {

            occur.put(ch, occur.getOrDefault(ch, 0) + 1);
        }
        for (var entry : occur.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        List<Integer> inputt = new ArrayList<Integer>(Arrays.asList(2, 3, 0, 5, 0, 8, 9));

            int opps=0;// 1,2,3,
            for(int i = 0; i < inputt.size(); i++){ //0, 1, 2, 3, 4,5
                System.out.println("loop value  "+i);
                if(inputt.get(i)!=0) {
                    System.out.println("opps value "+opps);
                    int temp = inputt.get(opps);
                    System.out.println("temp value "+temp);
                    inputt.set(opps, inputt.get(i));
                    System.out.println("first exchange "+inputt);
                    inputt.set(i, temp);
                    System.out.println("second exchange "+inputt);
                    opps++;
                }
            }
            System.out.println(inputt);

        int [] ar= {1,2,4,2,4,1};
        var uniqu = new ArrayList<Integer>();
        for(int i = 0; i<ar.length;i++)
        {
        if(uniqu.indexOf(ar[i])==-1){
            uniqu.add(ar[i]);
        }else{
            System.out.println(ar[i]);
        }
            System.out.println( uniqu);

    }

        }



    }

