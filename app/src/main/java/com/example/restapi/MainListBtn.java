package com.example.restapi;

public class MainListBtn {
    private String textStr1 ;
    private String textStr2 ;
    private String textStr3 ;


    public void setText1(String text) {
        textStr1 = text ;
    }

    public void setText2(String text) {
        textStr2 = text ;
    }

    public void setText3(String text) {
        textStr3 = text ;
    }

    public String getText1() {
        return this.textStr1 ;
    }

    public String getText2() {
        return this.textStr2 ;
    }

    public String getText3() {
        return this.textStr3 ;
    }
}
