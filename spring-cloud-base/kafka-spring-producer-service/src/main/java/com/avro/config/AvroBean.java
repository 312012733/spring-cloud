package com.avro.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("avro.schema")
public class AvroBean
{
    private String avscDir = "avro";
    
    private String controlReportAvsc = avscDir + File.separator + "psgcar_control_result_report.avsc";
    
    private String controlReportPushAvsc = avscDir + File.separator + "psgcar_control_result_report_push.avsc";
    
    public String getControlReportAvsc()
    {
        return controlReportAvsc;
    }
    
    public void setControlReportAvsc(String controlReportAvsc)
    {
        this.controlReportAvsc = controlReportAvsc;
    }
    
    public String getControlReportPushAvsc()
    {
        return controlReportPushAvsc;
    }
    
    public void setControlReportPushAvsc(String controlReportPushAvsc)
    {
        this.controlReportPushAvsc = controlReportPushAvsc;
    }
    
    public String getAvscDir()
    {
        return avscDir;
    }
    
    public void setAvscDir(String avscDir)
    {
        this.avscDir = avscDir;
    }
    
}
