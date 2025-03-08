package com.vertineko.shospital.constant;


public enum Sex {
    MALE("男"),
    FEMALE("女");


    private final String val;

    Sex(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }
}
