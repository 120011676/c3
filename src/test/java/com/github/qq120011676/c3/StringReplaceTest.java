package com.github.qq120011676.c3;

public class StringReplaceTest {
    public static void main(String[] args) {
        System.out.println("441521110218122"
                .replaceAll("(\\d{2})\\d{2}(\\d{11})",
                        "$100$2"));
    }
}
