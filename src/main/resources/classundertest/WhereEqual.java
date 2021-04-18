package com.github.bradleyrumball.autologic;

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

    public static String elseEqualPlusOne(int a, int b) {
        if( a != b+1) {
            return "a!=b on if";
        } else {
            return "a==b on else";
        }
    }
}