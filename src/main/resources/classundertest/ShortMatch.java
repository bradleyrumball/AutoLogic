package com.github.bradleyrumball.autologic;

public class ShortMatch {

    public static String smolNumber(short num) {
        if (num == (short)6) {
            return "Yay we got da short";
        } else if (num > (short)10) {
            return "Not very short";
        } else {
            return "Nothing to see here";
        }
    }
}
