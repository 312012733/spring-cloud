package com.consumer.vo;

import java.io.Serializable;

public class ControlResultRabbitMsg implements Serializable
{
    private String vin;
    private String uuid;
    private String result;
    private Long time;
    
    public ControlResultRabbitMsg()
    {
    }
    
    public ControlResultRabbitMsg(String vin, String uuid, String result, Long time)
    {
        super();
        this.vin = vin;
        this.uuid = uuid;
        this.result = result;
        this.time = time;
    }
    
    public String getVin()
    {
        return vin;
    }
    
    public void setVin(String vin)
    {
        this.vin = vin;
    }
    
    public String getUuid()
    {
        return uuid;
    }
    
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public Long getTime()
    {
        return time;
    }
    
    public void setTime(Long time)
    {
        this.time = time;
    }
    
    @Override
    public String toString()
    {
        return "ControlResultKafkMsg [vin=" + vin + ", uuid=" + uuid + ", result=" + result + ", time=" + time + "]";
    }
    
}
