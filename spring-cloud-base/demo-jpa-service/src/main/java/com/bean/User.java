package com.bean;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_user")
public class User
{
    @Id
    @Column(name = "pk_id")
    private String id;
    
    @Column(name = "user_name")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Transient
    private Role role;
    
    public User()
    {
    }
    
    public User(String id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public Role getRole()
    {
        return role;
    }
    
    public void setRole(Role role)
    {
        this.role = role;
    }
    
    @Override
    public String toString()
    {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
    }
    
}
