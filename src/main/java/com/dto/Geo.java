package com.dto;

import lombok.Data;

@Data
public class Geo {
    private String lat;
    private String lng;

    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "lat: " + lat + ", lng: " + lng;
    }
}