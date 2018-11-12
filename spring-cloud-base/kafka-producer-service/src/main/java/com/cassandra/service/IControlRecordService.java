package com.cassandra.service;

import com.cassandra.entity.ControlRecord;
import com.consumer.vo.ControlResultKafkMsg;

public interface IControlRecordService
{
    void saveControlRecord(ControlRecord controlRecord);
    
    void saveControlResult(ControlResultKafkMsg controlResultKafkaMessage);
}
