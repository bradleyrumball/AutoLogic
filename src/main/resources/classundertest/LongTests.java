package com.github.bradleyrumball.autologic;

public class LongTests {

    public static String LomgTests(long num) {
        if (num > (long)60000) {
            return "Very long";
        } else if (num < (long)2) {
            return "Not very long";
        } else {
            return "Nothing to see here";
        }
    }
}
