package com.vertineko.shospital.constant;


import lombok.Getter;

@Getter
public enum Sex {
    MALE("男"),
    FEMALE("女"),
    NULL("null");


    private final String val;

    Sex(String val){
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
