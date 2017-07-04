package com.nbcuni.test.cms.utils.httpclient;

public enum HttpHeaders {
    PRAGMA("Pragma"),
    X_CACHE("X-Cache");

    String name;

    HttpHeaders(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}