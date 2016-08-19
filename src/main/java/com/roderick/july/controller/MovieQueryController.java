package com.roderick.july.controller;

import com.roderick.july.model.Cinema;
import com.roderick.july.model.Movie;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Roderick on 2016/8/18.
 */
@RestController
@RequestMapping(value = {"/movieQuery"})
public class MovieQueryController {

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        PropertyEditor editor = new CustomDateEditor(format, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = {"/{movieName}/{date}"}, method = RequestMethod.GET)
    public Cinema cinemaInfo(@PathVariable String movieName, @PathVariable Date date, HttpServletRequest req) {
        Cinema cinema = initInfoTest(movieName, date);
        if (cinema == null) throw new MovieNotFoundException(movieName);
        return cinema;
    }

    @RequestMapping(value = {"/{date}"}, method = RequestMethod.POST)
    public void cinemaInfo(@PathVariable Date date) {

    }

    private Cinema initInfoTest(String movieName, Date date) {
        Movie movie = new Movie();
        movie.setEnName(movieName);
        movie.setChName("诺亚方舟漂流记");
        movie.setPrice(29);
        movie.setDigital("3D");
        movie.setDuration(86);
        movie.setType("英语");
        LinkedList<String> list = new LinkedList<String>();
        list.offer("18:15");
        list.offer("19:30");
        movie.setTimes(list);

        Movie movie2 = new Movie();
        movie2.setEnName(movieName);
        movie2.setChName("诺亚方舟漂流记");
        movie2.setPrice(59);
        movie2.setDigital("3D");
        movie2.setDuration(86);
        movie2.setType("普通话");
        LinkedList<String> list2 = new LinkedList<String>();
        list2.offer("20:15");
        list2.offer("22:30");
        movie2.setTimes(list2);

        Cinema cinema = new Cinema();
        cinema.setName("深圳橙天嘉禾影城");
        cinema.setAddress("宝安南路1881号华润中心万象城中座三楼");
        cinema.setBottomPrice(29);
        cinema.setTopPrice(59);
        cinema.setHasTicket(true);
        cinema.setReleaseDate(date);
        LinkedList<Movie> movieList = new LinkedList<Movie>();
        movieList.offer(movie);
        movieList.offer(movie2);
        cinema.setMovieInfos(movieList);

        return cinema;
    }
}
