package com.roderick.july.controller;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roderick on 2016/8/20.
 */
public class ThriftHttpServiceExporter extends ThriftExporter implements HttpRequestHandler {

    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletInputStream in = request.getInputStream();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(CONTENT_TYPE_THRIFT);
        invoke(in, out);
    }

}
