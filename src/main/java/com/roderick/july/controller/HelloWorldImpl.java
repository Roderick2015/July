package com.roderick.july.controller;

import org.apache.thrift.TException;

/**
 * Created by Roderick on 2016/8/20.
 */
public class HelloWorldImpl implements HelloWorld.Iface {
    public String sayHello(String username) throws TException {
        return "hello world, "+username;
    }
}
