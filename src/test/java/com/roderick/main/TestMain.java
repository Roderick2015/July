package com.roderick.main;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roderick on 2016/7/28.
 */
public class TestMain extends DispatcherServlet {
    public void a(HttpServletRequest rs, HttpServletResponse ss) throws ServletException, IOException {
        super.doPost(rs, ss);
    }

    public static void main(String[] args) {
        BeanWrapper bean = new BeanWrapperImpl(new Movie());
        bean.setPropertyValue("name", "魔戒");
        PropertyValue value = new PropertyValue("pos", 2);
        bean.setPropertyValue(value);
    }
}
