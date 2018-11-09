package com.cassandra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.stereotype.Service;

import com.cassandra.entity.ControlRecord;
import com.cassandra.repository.IControlRecordRepository;
import com.cassandra.service.IControlRecordService;
import com.consumer.vo.ControlResultKafkMsg;
import com.utils.BeanUtils;

@Service
public class ControlRecordServiceImpl implements IControlRecordService
{
    @Autowired
    private IControlRecordRepository controlRecordRepository;
    
    @Override
    public void saveControlRecord(ControlRecord controlRecord)
    {
        controlRecordRepository.save(controlRecord);
    }
    
    @Override
    public void saveControlResult(ControlResultKafkMsg kfkMsg)
    {
        ControlRecord controlRecord = controlRecordRepository.findById(new BasicMapId().with("vin", kfkMsg.getVin())
                .with("uuid", kfkMsg.getUuid())).get();
        
        if (controlRecord == null)
        {
            throw new SecurityException("get controlRecord error. controlRecord is null. vin:" + kfkMsg.getVin()
                    + ", uuid:" + kfkMsg.getUuid());
        }
        
        BeanUtils.copyPropertiesIgnoreNullValue(kfkMsg, controlRecord, "time");
        
        controlRecordRepository.save(controlRecord);
    }
    
}
