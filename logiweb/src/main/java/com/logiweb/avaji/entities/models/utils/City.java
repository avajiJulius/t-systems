package com.logiweb.avaji.entities.models.utils;

public class City{
    private Long cityCode;
    private String cityName;

    public City(Long cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
