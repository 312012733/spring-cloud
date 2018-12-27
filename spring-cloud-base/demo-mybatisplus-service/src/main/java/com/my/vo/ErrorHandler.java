package com.my.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("失败消息")
public class ErrorHandler
{
    @ApiModelProperty("失败原因")
    private String errorMsg;
    
    public ErrorHandler()
    {
    }
    
    public ErrorHandler(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    @Override
    public String toString()
    {
        return "ErrorHandler [errorMsg=" + errorMsg + "]";
    }
    
}
