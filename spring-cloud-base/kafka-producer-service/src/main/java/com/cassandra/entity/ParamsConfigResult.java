package com.cassandra.entity;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table(value = "params_config_result")
public class ParamsConfigResult
{
    @PrimaryKeyColumn(name = "vin", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String vin; // 车架号
    
    @Column(value = "time")
    @PrimaryKeyColumn(name = "time", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = DataType.Name.BIGINT)
    private Long time; // 时间
    
    @CassandraType(type = DataType.Name.TEXT)
    private String uuid; // TBOX网关返回主键
    
    /**
     * paramID:0110 Wifi用户名, paramID:0111 Wifi密码, paramID:0112 Wifi开关
     */
    @Column(value = "param_result")
    @CassandraType(type = DataType.Name.TEXT)
    private String paramResult;
    
    public ParamsConfigResult()
    {
    }
    
    public ParamsConfigResult(String vin, String uuid, Long time, String paramResult)
    {
        this.vin = vin;
        this.uuid = uuid;
        this.time = time;
        this.paramResult = paramResult;
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
    
    public Long getTime()
    {
        return time;
    }
    
    public void setTime(Long time)
    {
        this.time = time;
    }
    
    public synchronized String getParamResult()
    {
        if (StringUtils.isEmpty(paramResult))
        {
            paramResult = "{}";
        }
        
        return paramResult;
    }
    
    public void setParamResult(String paramResult)
    {
        this.paramResult = paramResult;
    }
    
    @Override
    public String toString()
    {
        return "ParamsConfigResult [vin=" + vin + ", uuid=" + uuid + ", time=" + time + ", paramResult=" + paramResult
                + "]";
    }
    
}
