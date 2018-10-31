package com.config.cassandra;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cassandra")
public class CassandraBean
{
    private String contactpoints;
    private int port;
    private String keyspace;
    
    public String getContactpoints()
    {
        return contactpoints;
    }
    
    public void setContactpoints(String contactpoints)
    {
        this.contactpoints = contactpoints;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public String getKeyspace()
    {
        return keyspace;
    }
    
    public void setKeyspace(String keyspace)
    {
        this.keyspace = keyspace;
    }
    
}
