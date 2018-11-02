package com.consumer.vo;

public class ControlResultPushKafkMsg
{
    private String vin;
    private String uuid;
    private String result;
    private Integer action;
    private Long time;
    
    public ControlResultPushKafkMsg()
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
    
    public Integer getAction()
    {
        return action;
    }
    
    public void setAction(Integer action)
    {
        this.action = action;
    }
    
    @Override
    public String toString()
    {
        return "ControlResultPushKafkMsg [vin=" + vin + ", uuid=" + uuid + ", result=" + result + ", action=" + action
                + ", time=" + time + "]";
    }
    
}
