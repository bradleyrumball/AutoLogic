package com.github.bradleyrumball.autologic;

public class LongTests {

    public static String LomgTests(long num) {
        if (num > 60000) {
            return "Very long";
        } else if (num < 2) {
            return "Not very long";
        } else {
            return "Nothing to see here";
        }
    }
}
