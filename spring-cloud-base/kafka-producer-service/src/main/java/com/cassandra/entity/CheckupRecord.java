package com.cassandra.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

/**
 * Created by YuanSongMing on 2016/6/15 13:39.
 */
@Table(value = "checkup_record")
public class CheckupRecord
{
    @PrimaryKeyColumn(name = "vin", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String vin; // 车架号
    
    @PrimaryKeyColumn(name = "uuid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = DataType.Name.TEXT)
    private String uuid; // TBOX网关返回主键
    
    @Column(value = "checkcommand")
    @CassandraType(type = DataType.Name.TEXT)
    private String checkcommand; // 脚本
    
    @Column(value = "time")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long time; // 时间
    
    public CheckupRecord()
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
    
    public Long getTime()
    {
        return time;
    }
    
    public void setTime(Long time)
    {
        this.time = time;
    }
    
    public String getUuid()
    {
        return uuid;
    }
    
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
    
    public String getCheckcommand()
    {
        return checkcommand;
    }
    
    public void setCheckcommand(String checkcommand)
    {
        this.checkcommand = checkcommand;
    }
}
