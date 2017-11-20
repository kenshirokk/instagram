package com.kenshiro.instagram;

import com.kenshiro.instagram.util.TypeNameEunm;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {

        String name = "image";
        System.out.println(TypeNameEunm.IMAGE.value());
        System.out.println(TypeNameEunm.IMAGE.equals(name));
    }
}
