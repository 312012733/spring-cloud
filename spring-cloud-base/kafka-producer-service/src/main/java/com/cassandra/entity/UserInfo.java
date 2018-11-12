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
@Table(value = "user_info")
public class UserInfo
{
    @PrimaryKeyColumn(name = "uuid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String uuid;
    
    @Column(value = "user_id")
    @CassandraType(type = DataType.Name.TEXT)
    private String userid; // 车架号
    
    @Column(value = "x_token")
    @CassandraType(type = DataType.Name.TEXT)
    private String token; // 脚本
    
    @Column(value = "channel_id")
    @CassandraType(type = DataType.Name.TEXT)
    private String channelid; //
    
    public UserInfo()
    {
    }
    
    public UserInfo(String uuid, String userid, String token, String channelid)
    {
        super();
        this.uuid = uuid;
        this.userid = userid;
        this.token = token;
        this.channelid = channelid;
    }
    
    public String getChannelid()
    {
        return channelid;
    }
    
    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }
    
    public String getUuid()
    {
        return uuid;
    }
    
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
    
    public String getUserid()
    {
        return userid;
    }
    
    public void setUserid(String userid)
    {
        this.userid = userid;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
}
