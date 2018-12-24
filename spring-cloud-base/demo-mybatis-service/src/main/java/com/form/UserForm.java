package com.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "登录表单")
public class UserForm
{
    @ApiModelProperty(value = "验证码", example = "abcd")
    private String checkCode;
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;
    @ApiModelProperty(value = "密码", example = "1234")
    private String password;
    
    public UserForm()
    {
    }
    
    public UserForm(String checkCode, String username, String password)
    {
        this.checkCode = checkCode;
        this.username = username;
        this.password = password;
    }
    
    public String getCheckCode()
    {
        return checkCode;
    }
    
    public void setCheckCode(String checkCode)
    {
        this.checkCode = checkCode;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Override
    public String toString()
    {
        return "UserDTO [checkCode=" + checkCode + ", username=" + username + ", password=" + password + "]";
    }
    
}
