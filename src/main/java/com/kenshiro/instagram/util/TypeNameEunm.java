package com.kenshiro.instagram.util;

public enum TypeNameEunm {
    GRAPHIMAGE("GraphImage"), GRAPHSIDECAR("GraphSidecar"), GRAPHVIDEO("GraphVideo");

    private String value;

    TypeNameEunm(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
