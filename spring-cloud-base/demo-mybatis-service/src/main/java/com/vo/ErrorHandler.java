package com.vo;

public class ErrorHandler
{
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
