package com.github.bradleyrumball.autologic;

public class CharMatch {

    public static String bigC(char letter) {
        if (letter > 'C') {
            return "Letter ASCII is bigger than C";
        } else if (letter == 'C') {
            return "Letter is C";
        } else {
            return "Nothing interesting";
        }
    }
}
