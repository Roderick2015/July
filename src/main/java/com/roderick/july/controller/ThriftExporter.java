package com.roderick.july.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Roderick on 2016/8/20.
 */
public class ThriftExporter extends RemoteExporter implements InitializingBean {
    public static final String CONTENT_TYPE_THRIFT  = "application/x-thrift";
    protected final Log logger = LogFactory.getLog(getClass());

    private TProcessor processor;
    private TProtocolFactory inProtocolFactory;
    private TProtocolFactory outProtocolFactory;
    private Collection<Map.Entry<String, String>> customHeaders;


    public void afterPropertiesSet() throws Exception {
        Class<?> proClass = Class.forName(getServiceInterface().getName().replace("$Iface", "$Processor"));
        Constructor<?> construct = proClass.getConstructor(getServiceInterface());
        TProcessor processor = (TProcessor) construct.newInstance(getService());

        this.processor = processor;
        this.inProtocolFactory = new TCompactProtocol.Factory();
        this.outProtocolFactory = new TCompactProtocol.Factory();
        this.customHeaders = new ArrayList<Map.Entry<String, String>>();
    }

    public void invoke(InputStream inStream, OutputStream outStream) {
        Assert.notNull(this.processor, "Thrift processor has not bean initialized");

        doInvoke(inStream, outStream);
    }

    public void doInvoke(InputStream inputStream, OutputStream outputStream) {
        try {
            TTransport transport = new TIOStreamTransport(inputStream, outputStream);
            TProtocol inProtocol = inProtocolFactory.getProtocol(transport);
            TProtocol outProtocol = outProtocolFactory.getProtocol(transport);
            this.processor.process(inProtocol, outProtocol);
            outputStream.flush();;
        } catch (TException e) {
            logger.error("error:{}",e);
           // throw new Thrift
        } catch (IOException e) {

        }
    }

    public void addCustomHeader(final String key, final String value) {
        this.customHeaders.add(new Map.Entry<String, String>() {
            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }

            public String setValue(String value) {
                return null;
            }
        });
    }

    public void setCustomHeaders(Collection<Map.Entry<String, String>> headers) {
        this.customHeaders.clear();
        this.customHeaders.addAll(headers);
    }

}
