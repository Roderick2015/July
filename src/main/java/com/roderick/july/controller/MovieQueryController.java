package com.roderick.july.controller;

import com.roderick.july.model.Cinema;
import com.roderick.july.model.Movie;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Roderick on 2016/8/18.
 */
/*浏览器跨域请求，springmvc4.2以上版本添加了CORS支持，之前的版本需手动注册Filter*/
@CrossOrigin(origins = "http://localhost:3000")
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
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("18:15", 0);
        map.put("22:30", 1);
        movie.setScreenings(map);

        Movie movie2 = new Movie();
        movie2.setEnName(movieName);
        movie2.setChName("诺亚方舟漂流记");
        movie2.setPrice(59);
        movie2.setDigital("3D");
        movie2.setDuration(86);
        movie2.setType("普通话");
        Map<String, Integer> map2 = new HashMap<String, Integer>();
        map2.put("20:15", 1);
        map2.put("23:30", 0);
        movie2.setScreenings(map2);

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
