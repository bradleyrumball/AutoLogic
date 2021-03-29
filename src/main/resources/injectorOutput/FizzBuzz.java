package com.github.bradleyrumball.autologic;

import java.util.*;

class FizzBuzz {

    public static void main(String[] args) {
        int n = 100;
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                log(1, i % 15, 0, Operator.EQUALS);
                System.out.print("FizzBuzz" + " ");
            } else if (i % 5 == 0) {
                log(2, i % 5, 0, Operator.EQUALS);
                System.out.print("Buzz" + " ");
            } else if (i % 3 == 0) {
                log(3, i % 3, 0, Operator.EQUALS);
                System.out.print("Fizz" + " ");
            } else {
                log(4, i % 3, 0, Operator.NOT_EQUALS);
                System.out.print(i + " ");
            }
        }
    }
}
