package com.my.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "登录表单")
public class UserForm
{
    
    @ApiModelProperty(value = "验证码", example = "abcd")
    @NotBlank(message = "验证码不能为空")
    private String checkCode;
    
    @ApiModelProperty(value = "用户名", example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @ApiModelProperty(value = "密码", example = "1234")
    @NotBlank(message = "密码不能为空")
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
