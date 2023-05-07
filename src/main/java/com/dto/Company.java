package com.dto;

import lombok.Data;

@Data
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;

    public Company(String name) {
        this.name = name;
//        this.catchPhrase = catchPhrase;
//        this.bs = bs;
    }


    @Override
    public String toString() {
        return "name: " + name +
                ", catchPhrase: " + catchPhrase +
                ", bs: " + bs;
    }
}
