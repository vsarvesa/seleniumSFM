package tests;

import java.util.ArrayList;
import java.util.List;

public class basestest {

        public static void main(String[] args) {
            System.out.println("Start small. Ship something.");

            int [] base = {1, 2, 3, 4, 2, 5, 1};
            List<Integer> unique= new ArrayList<>();
            List<Integer> dup= new ArrayList<>();

            for(int i =0; i<base.length ;i++) {
                if (unique.indexOf(base[i]) == -1) {
                    unique.add(base[i]);
                }
                else{
                    dup.add(base[i]);
                }
            }
            System.out.println(unique);
            System.out.println(dup);

        }
}
