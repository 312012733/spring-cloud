package com.my.form;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("学生表单")
public class StudentAddOrUpdateForm
{
    @ApiModelProperty(example = "uuid")
    private String id;
    
    @ApiModelProperty(example = "9527")
    private String name;
    
    @ApiModelProperty(example = "13")
    private Integer age;
    
    @ApiModelProperty(example = "true")
    private Boolean gender;// 默认是 男
    
    @ApiModelProperty(example = "1")
    private String myClassId;
    
    @ApiModelProperty(example = "[\"1\",\"2\"]")
    private List<String> teacherIds;
    
    public StudentAddOrUpdateForm()
    {
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
    
    public String getMyClassId()
    {
        return myClassId;
    }
    
    public void setMyClassId(String myClassId)
    {
        this.myClassId = myClassId;
    }
    
    public List<String> getTeacherIds()
    {
        return teacherIds;
    }
    
    public void setTeacherIds(List<String> teacherIds)
    {
        this.teacherIds = teacherIds;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return "StudentForm [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", myClassId="
                + myClassId + ", teacherIds=" + teacherIds + "]";
    }
    
}
