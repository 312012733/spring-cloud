package com.my.form;

import com.my.bean.MyClass;
import com.my.bean.Student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("学生查询条件表单")
public class StudentConditonForm
{
    
    @ApiModelProperty(example = "9527")
    private String name;
    
    @ApiModelProperty(example = "13")
    private Integer age;
    
    @ApiModelProperty(example = "true")
    private Boolean gender;// 默认是 男
    
    @ApiModelProperty(example = "1")
    private String myClassName;
    
    public StudentConditonForm()
    {
    }
    
    public Student buildStuCondition()
    {
        return new Student(null, name, age, gender, new MyClass(null, myClassName));
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public Boolean getGender()
    {
        return gender;
    }
    
    public void setGender(Boolean gender)
    {
        this.gender = gender;
    }
    
    public String getMyClassName()
    {
        return myClassName;
    }
    
    public void setMyClassName(String myClassName)
    {
        this.myClassName = myClassName;
    }
    
    @Override
    public String toString()
    {
        return "StudentConditonForm [name=" + name + ", age=" + age + ", gender=" + gender + ", myClassName="
                + myClassName + "]";
    }
    
}
