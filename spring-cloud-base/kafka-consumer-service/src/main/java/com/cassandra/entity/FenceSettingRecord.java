package com.cassandra.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table(value = "fence_setting_record")
public class FenceSettingRecord
{
    @PrimaryKeyColumn(name = "vin", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String vin; // 车架号
    
    @PrimaryKeyColumn(name = "uuid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = DataType.Name.TEXT)
    private String uuid; // TBOX网关返回主键
    
    /**
     * 1:添加成可用的(清除旧可用标识。设置快速创建标记) 2:修改成可用的(必传sourceId ，清除旧可用标识) 3:删除一个可用的(必传sourceId，删除旧的可用数据)
     * 4:清除可用（必传sourceId，清除旧的可用标识）
     */
    @Column(value = "action")
    @CassandraType(type = DataType.Name.TEXT)
    private String action;// 电子围栏操作动作
    
    @Column(value = "sourceId")
    @CassandraType(type = DataType.Name.TEXT)
    private String sourceId;// 待操作电子围栏的id
    
    @Column(value = "quick_creat")
    @CassandraType(type = DataType.Name.TEXT)
    private boolean quickCreat;//
    
    @Column(value = "name")
    @CassandraType(type = DataType.Name.TEXT)
    private String name;
    
    @Column(value = "creat_time")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long creatTime; // 时间
    
    /**
     * 0:失败 1:成功 2:待总线正常通信后执行 3:执行失败，解除安全模式出错 4:执行失败，限速写入出错 5:ECU 超时无响应 6:空气净化器未开启时，不允许进行模式操作 7:在一条车控正在执行过程中，车控指令不能执 行
     */
    @Column(value = "result")
    @CassandraType(type = DataType.Name.TEXT)
    private String result;
    
    @Column(value = "fence_settings")
    @CassandraType(type = DataType.Name.TEXT)
    private String fenceSettings;
    
    public FenceSettingRecord()
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
    
    public String getFenceSettings()
    {
        return fenceSettings;
    }
    
    public void setFenceSettings(String fenceSettings)
    {
        this.fenceSettings = fenceSettings;
    }
    
    public String getAction()
    {
        return action;
    }
    
    public void setAction(String action)
    {
        this.action = action;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSourceId()
    {
        return sourceId;
    }
    
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }
    
    public boolean isQuickCreat()
    {
        return quickCreat;
    }
    
    public void setQuickCreat(boolean quickCreat)
    {
        this.quickCreat = quickCreat;
    }
    
    public Long getCreatTime()
    {
        return creatTime;
    }
    
    public void setCreatTime(Long creatTime)
    {
        this.creatTime = creatTime;
    }
    
    @Override
    public String toString()
    {
        return "FenceSettingRecord [vin=" + vin + ", uuid=" + uuid + ", action=" + action + ", sourceId=" + sourceId
                + ", quickCreat=" + quickCreat + ", name=" + name + ", creatTime=" + creatTime + ", result=" + result
                + "]";
    }
    
}
