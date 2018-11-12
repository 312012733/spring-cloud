package com.cassandra.repository;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.cassandra.entity.ControlRecord;

public interface IControlRecordRepository extends CassandraRepository<ControlRecord, MapId>
{
    @Query("SELECT * FROM control_record WHERE vin = ?0 LIMIT 1 ALLOW FILTERING")
    ControlRecord getControlRecordByLastTime(String vin);
    
}
