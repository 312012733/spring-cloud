package com.cassandra.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table(value = "params_modify_record")
public class ParamsModifyRecord
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
     * 0:失败 1:成功 2:待总线正常通信后执行 3:执行失败，解除安全模式出错 4:执行失败，限速写入出错 5:ECU 超时无响应
     * 6:空气净化器未开启时，不允许进行模式操作 7:在一条车控正在执行过程中，车控指令不能执 行
     */
    @Column(value = "result")
    @CassandraType(type = DataType.Name.TEXT)
    private String result;
    
    /**
     * paramID:0110 paramValue:Wifi用户名, paramID:0111 paramValue:Wifi密码,
     * paramID:0112 paramValue:Wifi开关
     * 格式：[{paramID:0110,paramValue:Wifi用户名},{paramID:0111,paramValue:Wifi密码},{paramID:0112,paramValue:Wifi开关}]
     */
    @Column(value = "param_items")
    @CassandraType(type = DataType.Name.TEXT)
    private String paramItems;
    
    public ParamsModifyRecord()
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
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
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
        return "ParamsModifyRecord [vin=" + vin + ", uuid=" + uuid + ", time=" + time + ", result=" + result
                + ", paramItems=" + paramItems + "]";
    }
    
}
