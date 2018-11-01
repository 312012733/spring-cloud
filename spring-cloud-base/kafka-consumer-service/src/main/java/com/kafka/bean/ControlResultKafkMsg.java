package com.kafka.bean;

public class ControlResultKafkMsg
{
    private String vin;
    private String uuid;
    private String result;
    private Long time;
    
    public ControlResultKafkMsg()
    {
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
