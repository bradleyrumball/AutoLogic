package com.github.bradleyrumball.autologic;

public class LoopTesting {

    public static String loopChecker (int target) {
        for (int i = 0; i < 50; i++) {
            if (i == target) {
                return "We've hit the target!";
            }
        }
        return "Target not hit";
    }
}

