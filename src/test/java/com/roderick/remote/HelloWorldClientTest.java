package com.roderick.remote;

import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Roderick on 2016/8/20.
 */
public class HelloWorldClientTest {
    public static void main(String[] args) throws TException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        HelloWorld.Iface client = context.getBean(HelloWorld.Iface.class);
        System.out.println(client.sayHello("roderick"));
    }
}
