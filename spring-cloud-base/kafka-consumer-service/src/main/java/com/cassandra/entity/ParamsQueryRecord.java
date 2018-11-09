package com.cassandra.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table(value = "params_query_record")
public class ParamsQueryRecord
{
    
    @PrimaryKeyColumn(name = "vin", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String vin; // 车架号
    
    @PrimaryKeyColumn(name = "uuid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = DataType.Name.TEXT)
    private String uuid; // TBOX网关返回主键
    
    @Column(value = "time")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long time; // 时间
    
    /**
     * 0:所有 , 1:指定
     */
    @Column(value = "type")
    @CassandraType(type = DataType.Name.TEXT)
    private String type;
    
    /**
     * paramID:0110 Wifi用户名, paramID:0111 Wifi密码, paramID:0112 Wifi开关 。格式：[{paramID:0110},{paramID:0111},{paramID:0112}]
     */
    @Column(value = "param_items")
    @CassandraType(type = DataType.Name.TEXT)
    private String paramItems;
    
    public ParamsQueryRecord()
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
    
    public Long getTime()
    {
        return time;
    }
    
    public void setTime(Long time)
    {
        this.time = time;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getParamItems()
    {
        return paramItems;
    }
    
    public void setParamItems(String paramItems)
    {
        this.paramItems = paramItems;
    }
    
    @Override
    public String toString()
    {
        return "ParamsQueryRecord [vin=" + vin + ", uuid=" + uuid + ", time=" + time + ", type=" + type
                + ", paramItems=" + paramItems + "]";
    }
    
}
