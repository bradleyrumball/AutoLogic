package com.github.bradleyrumball.autologic;

public class StringChecker {

    public static String checkString(String a, String b) {

        if (a == b) {
            return "Strings are the same";
        } else if (a.charAt(0) == 'A') {
            return "First letter is A for string a";
        } else if (b.charAt(0) == 'B') {
            return "First letter is B for string b";
        } else {
            return "Nothing interesting to be found";
        }


    }

}
