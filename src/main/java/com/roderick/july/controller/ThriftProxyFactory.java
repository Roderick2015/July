package com.roderick.july.controller;

import org.apache.http.client.HttpClient;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.springframework.beans.BeansException;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

/**
 * Created by Roderick on 2016/8/20.
 */
public class ThriftProxyFactory implements ObjectFactory {
    private HttpClient httpClient;
    private final ClassLoader loader;

    public ThriftProxyFactory() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ThriftProxyFactory(ClassLoader loader) {
        this.loader = loader;
    }


    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        Reference ref = (Reference) obj;
        String api = null;
        String url = null;
        for (int i = 0; i < ref.size(); i++) {
            RefAddr addr = ref.get(i);

            String type = addr.getType();
            String value = (String) addr.getContent();

            if (type.equals("type"))
                api = value;
            else if (type.equals("url"))
                url = value;
        }

        if (url == null)
            throw new NamingException("`url' must be configured for ThriftProxyFactory.");
        if (api == null)
            throw new NamingException("`type' must be configured for ThriftProxyFactory.");
        Class<?> apiClass = Class.forName(api, false, loader);
        return create(apiClass, url);
    }

    public Object create(Class<?> serviceInterface, String serviceUrl) throws Exception {
        THttpClient hClient = new THttpClient(serviceUrl, httpClient);
        TProtocol protocol = new TCompactProtocol(hClient);
        Class<?> client = Class.forName(serviceInterface.getName().replace("$Iface", "$Client"));
        Constructor<?> construct = client.getConstructor(TProtocol.class);
        return construct.newInstance(protocol);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
