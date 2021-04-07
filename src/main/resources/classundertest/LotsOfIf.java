package com.github.bradleyrumball.autologic;

public class LotsOfIf {

    public static String fourLayersDeep(int a, int b, int c, int d) {
        if( a == b+1 ) {
            if (b == c+1) {
                if (c == d+1) {
                    if (d == a+1) {
                        return "shouldn't have got here";
                    }
                    return "three layers deep";
                }
                return "two layers deep";
            }
            return "one layers deep";
        }
        return "no layers deep";
    }
}