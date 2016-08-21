package com.roderick.july.controller;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by Roderick on 2016/8/20.
 */
public class ThriftHttpProxyFactoryBean extends ThriftClientInterceptor implements FactoryBean<Object> {
    private Object serviceProxy;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = new ProxyFactory(getServiceInterface(), this).getProxy(getBeanClassLoader());
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }
}
