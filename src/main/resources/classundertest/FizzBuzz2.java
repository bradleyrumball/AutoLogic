package com.github.bradleyrumball.autologic;
// Java program to print Fizz Buzz
import java.util.*;
public class FizzBuzz
{
    public static void main(String args[])
    {
        int n = 100;

        // loop for 100 times
        for (int i=1; i<=n; i++)
        {
            //number divisible by 15(divisible by
            // both 3 & 5), print 'FizzBuzz' in
            // place of the number
            if (i%15==0) {
                System.out.print("FizzBuzz"+" ");
            } else if (i%5==0){
                System.out.print("Buzz"+" ");
            } else if (i%3==0) {
                System.out.print("Fizz"+" ");
            } else {
                System.out.print(i+" ");
            }

        }
    }
}