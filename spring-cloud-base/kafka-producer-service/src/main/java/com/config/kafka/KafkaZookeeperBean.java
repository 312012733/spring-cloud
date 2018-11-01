package com.config.kafka;

public class KafkaZookeeperBean
{
    private String connect = "127.0.0.1:2181";
    
    private String connectTimeOut = "15000";
    
    private String sessionTimeout = "15000";
    
    private String synctime = "200";
    
    public String getConnect()
    {
        return connect;
    }
    
    public void setConnect(String connect)
    {
        this.connect = connect;
    }
    
    public String getConnectTimeOut()
    {
        return connectTimeOut;
    }
    
    public void setConnectTimeOut(String connectTimeOut)
    {
        this.connectTimeOut = connectTimeOut;
    }
    
    public String getSessionTimeout()
    {
        return sessionTimeout;
    }
    
    public void setSessionTimeout(String sessionTimeout)
    {
        this.sessionTimeout = sessionTimeout;
    }
    
    public String getSynctime()
    {
        return synctime;
    }
    
    public void setSynctime(String synctime)
    {
        this.synctime = synctime;
    }
}
