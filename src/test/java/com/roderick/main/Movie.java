package com.roderick.main;

import java.util.Date;

/**
 * Created by Roderick on 2016/7/28.
 */
public class Movie {
    String name;
    Date date;
    int pos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
