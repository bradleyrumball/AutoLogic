package com.github.bradleyrumball.autologic;

public class AByteADay {

    public static String howCrunchyIsMyApple(byte num) {
        if (num == (byte)6) {
            return "Strong cronch";
        } else if (num > (byte)10) {
            return "Large Apple";
        } else if (num < (byte)2) {
            return "Small crunch";
        } else {
            return "No crunch";
        }
    }
}
