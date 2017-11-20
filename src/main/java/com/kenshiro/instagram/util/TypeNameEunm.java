package com.kenshiro.instagram.util;

public enum TypeNameEunm {
    IMAGE("image"), VIDEO("diveo"), CARD("card");

    private String name;

    TypeNameEunm(String value) {
        this.name = value;
    }

    public String value(){
        return name;
    }
}
