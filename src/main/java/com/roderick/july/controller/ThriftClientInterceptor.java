package com.roderick.july.controller;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Roderick on 2016/8/20.
 */
public class ThriftClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor {
    private ThriftProxyFactory proxyFactory = new ThriftProxyFactory();
    private Object thriftProxy;
    private HttpClient httpClient;

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (getServiceInterface() == null) {
            throw new IllegalArgumentException("property serviceInterface is required.");
        }

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);

        this.httpClient = new DefaultHttpClient(connectionManager);
        setReadTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);
        prepare();
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] args = methodInvocation.getArguments();
        String name = method.getName();
        if (args.length == 0) {
            if ("toString".equals(name)) {
                return "Thrift proxy for service URL [" + getServiceUrl() + "]";
            } else if ("hashCode".equals(name)) {
                return getServiceUrl().hashCode();
            }
        } else if (args.length == 1 && "equals".equals(name)) {
            return getServiceUrl().equals(args[0]);
        }
        if (this.thriftProxy == null) {
            throw new IllegalStateException("ThriftClientInterceptor is not properly initialized - " + "invoke 'prepare' before attempting any operations");
        }

        ClassLoader originalClassLoader = overrideThreadContextClassLoader();
        try {
            return method.invoke(thriftProxy, args);
        } catch (InvocationTargetException e) {
            logger.error("error:{}", e);
            throw new ThriftServletException("invoke error : {}", e);
        } catch (Throwable ex) {
            logger.error("error:{}", ex);
            throw new ThriftServletException("error : {}", ex);
        } finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }

    public void prepare() throws RemoteLookupFailureException {
        try {
            proxyFactory.setHttpClient(httpClient);
            this.thriftProxy = createThriftProxy(this.proxyFactory);
        } catch (Exception ex) {
            throw new RemoteLookupFailureException("Service URL [" + getServiceUrl() + "] is invalid", ex);
        }
    }

    protected Object createThriftProxy(ThriftProxyFactory proxyFactory) throws Exception {
        Assert.notNull(getServiceInterface(), "'serviceInterface is required'");
        return proxyFactory.create(getServiceInterface(), getServiceUrl());
    }

    public void setConnectTimeout(int timeout) {
        Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
        getHttpClient().getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
    }

    public void setReadTimeout(int timeout) {
        Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
        getHttpClient().getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
    }

    public void setProxyFactory(ThriftProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
