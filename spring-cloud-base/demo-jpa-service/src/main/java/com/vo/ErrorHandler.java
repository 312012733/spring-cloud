package com.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ʧ����Ϣ")
public class ErrorHandler
{
    @ApiModelProperty("ʧ��ԭ��")
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
