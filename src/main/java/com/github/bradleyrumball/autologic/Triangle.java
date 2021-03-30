package com.github.bradleyrumball.autologic;

public class Triangle {

    public enum Type {
        INVALID,
        SCALENE,
        EQUILATERAL,
        ISOSCELES;
    }

    public static Integer addTwoNumbers(Integer num1, Integer num2) {
        Integer sum = num1 + num2;
        return sum;
    }

    public static Type classify(int side1, int side2, int side3) {
        Type type;
        /* This orders sides from lowest to highest*/
        if (side1 > side2) {
            int temp = side1;
            side1 = side2;
            side2 = temp;

        }
        if (side1 > side3) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        if (side2 > side3) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }

        if (side1 + side2 <= side3) {
            type = Type.INVALID;
        } else {
            type = Type.SCALENE;
            if (side1 == side2) {
                if (side2 == side3) {
                    type = Type.EQUILATERAL;
                }
            } else {
                if (side2 == side3) {
                    type = Type.ISOSCELES;
                }
            }
        }
        return type;
    }
}
