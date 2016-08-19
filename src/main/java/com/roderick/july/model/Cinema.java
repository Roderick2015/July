package com.roderick.july.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Roderick on 2016/8/18.
 */
public class Cinema {
    public String name;
    public String address;
    public List<Movie> movieInfos;
    public int bottomPrice;
    public int topPrice;
    public Date releaseDate;
    public boolean hasTicket;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Movie> getMovieInfos() {
        return movieInfos;
    }

    public void setMovieInfos(List<Movie> movieInfos) {
        this.movieInfos = movieInfos;
    }

    public int getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(int bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public int getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(int topPrice) {
        this.topPrice = topPrice;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isHasTicket() {
        return hasTicket;
    }

    public void setHasTicket(boolean hasTicket) {
        this.hasTicket = hasTicket;
    }
}
