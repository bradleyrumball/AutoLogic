package com.github.bradleyrumball.autologic;

import sun.tools.jstat.Operator;

public class WhereEqual {

    public static String ifEqual(int a, int b) {
        if( a == b) {
            return "a==b on if";
        } else {
            return "a!=b on else";
        }
    }


    public static String ifNotEqual(int a, int b) {
        if( a != b ) {
            return "a!=b on if";
        } else {
            return "a==b on else";
        }
    }

    // This doesn't work as it has to randomly pick two numbers that one apart
    // Else has no guidance which is where the issue lies
    public static String elseEqualPlusOne(int a, int b) {
        if( a != b+1) {
            return "a!=b on if";
        } else {
            return "a==b on else";
        }
    }
}