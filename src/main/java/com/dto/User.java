package com.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class User {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

//    public User(int id, String name, String username,
//                String email, Address address, String phone,
//                String website, Company company) {
//        this.id = id;
//        this.name = name;
//        this.username = username;
//        this.email = email;
//        this.address = address;
//        this.phone = phone;
//        this.website = website;
//        this.company = company;
//    }

    @Override
    public String toString() {
        return "id: " + id +
                ", name: " + name +
                ", username: " + username +
                ", email: " + email +
                ", address: " + address +
                ", phone: " + phone +
                ", website: " + website +
                ", company: " + company;
    }
}
