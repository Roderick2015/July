package com.roderick.july.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Roderick on 2016/8/18.
 */
public class Movie {
    public String chName;
    public String enName;
    public String type;
    public String digital;
    public int duration;
    public int price;
    public Map<String, Integer> screenings; //电影场次时间，是否有票

    public String getDigital() {
        return digital;
    }

    public void setDigital(String digital) {
        this.digital = digital;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<String, Integer> getScreenings() {
        return screenings;
    }

    public void setScreenings(Map<String, Integer> screenings) {
        this.screenings = screenings;
    }
}
