package com.cassandra.entity;

import java.util.HashMap;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table(value = "control_record")
public class ControlRecord
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
     * 1：车门上锁；2：车门解锁；3：远程寻车；4：远程空调开启；5：远程空调关闭；6：远程升窗；7：远程关窗；8：远程开启后备箱；9：远程关闭后备箱；10：打开空气净化器；
     * 11：关闭空气净化器；12：远程启动；16：远程熄火；17：座椅温度控制(附加项16、17)；18：座椅通风控制(附加项18、19)；
     * 19：天窗控制； 999：sendToCar
     */
    @Column(value = "action")
    @CassandraType(type = DataType.Name.INT)
    private Integer action;
    
    /**
     * 0:失败；1:成功；2:待总线正常通信后执行；3:执行失败,解除安全模式出错；4:执行失败,限速写入出错；5:ECU；超时无响应；6:空气净化器未开启时,不允许进行模式操作；
     * 7:在一条车控正在执行过程中,车控指令不能执行；8:前置条件不满足；
     */
    @Column(value = "result")
    @CassandraType(type = DataType.Name.TEXT)
    private String result;
    
    /**
     * 1：双侧温度调节；2：前空调模式远程；3：双侧温度设置；4：后空调开关远程；5：前空调副驾温度高低远程控制；6：前空调主驾温度高低远程控制；7：前空调风量高低远程控制；
     * 8：空调压缩机远程控制；9：前空调内外循环远程控制；10：
     * 前除霜远程控制；11：前空调副驾温度远程控制；12：前空调主驾温度远程控制；13：前空调开关远程；
     * 14：前空调风量远程控制；15：空气净化器的工作模式；16：左前座椅加热请求；17：右前座椅加热请求；18：左前座椅通风请求；19：右前座椅通风请求；20：天窗控制请求
     */
    @Column(value = "controlid")
    @CassandraType(type = DataType.Name.TEXT)
    private String controlid;// 控制id
    
    /**
     * 1.双侧温度调节：1:增加+0.5,2:,降低-0.5； 2.前空调模式远程：1:吹面,2:吹面除霜,3:吹脚,4:吹脚除霜；
     * 3.双侧温度设置：17.5-32.5(调用者*10,传入整数)；
     * 4.后空调开关：1:关,2:开；5.前空调副驾温度：1:增加+0.5,2:,降低-0.5；
     * 6.前空调主驾温度：1:增加+0.5,2:,降低-0.5； 7.前空调风量高低：1:增加+1,2:降低-1；
     * 8.空调压缩机远程控制：1：关闭,2：打开；9.前空调内外循环：1:请求内循环,2:请求外循环； 10.前除霜远程控制：1：关闭,2：开启；
     * 11.前空调副驾驶温度：17.5-32.5(调用者*10,传入整数)；12.前空调主驾驶温度：17.5-32.5(调用者*10,传入整数)；
     * 13.前空调开关：1:关,2:开；
     * 14.前空调风量控制：1-7：1档-7档；15.空气净化器工作模式：0:自动,1:低,2:中,3:高,9999:只开启；16.左前座椅加热请求：1:关,2:低,3:中,4:高；
     * 17.右前座椅加热请求：1:关,2:低,3:中,4:高；18.左前座椅通风请求：1:关,2:低,3:中,4:高；
     * 19.右前座椅通风请求：1:关,2:低,3:中,4:高； 20.天窗控制请求：1:开,2:关；
     */
    @Column(value = "controlvalue")
    @CassandraType(type = DataType.Name.TEXT)
    private String controlvalue;// 控制value
    
    @Column(value = "controlmap")
    @CassandraType(type = DataType.Name.MAP, typeArguments =
    { DataType.Name.TEXT, DataType.Name.TEXT })
    private HashMap<String, String> controlMap = new HashMap<>();
    
    public ControlRecord()
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
    
    public Integer getAction()
    {
        return action;
    }
    
    public void setAction(Integer action)
    {
        this.action = action;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public String getControlid()
    {
        return controlid;
    }
    
    public void setControlid(String controlid)
    {
        this.controlid = controlid;
    }
    
    public String getControlvalue()
    {
        return controlvalue;
    }
    
    public void setControlvalue(String controlvalue)
    {
        this.controlvalue = controlvalue;
    }
    
    public HashMap<String, String> getControlMap()
    {
        if (null == controlMap)
        {
            controlMap = new HashMap<String, String>();
        }
        
        return controlMap;
    }
    
    public void setControlMap(HashMap<String, String> controlMap)
    {
        this.controlMap = controlMap;
    }
    
}
