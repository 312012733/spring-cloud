package com.git.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service.git")
public class JGitBean
{
    /**
     * URI of remote repository.
     */
    private String uri;
    /**
     * Base directory for local working copy of repository.
     */
    private File basedir;
    /**
     * Username for authentication with remote repository.
     */
    private String username;
    /**
     * Password for authentication with remote repository.
     */
    private String password;
    
    /** The default label to be used with the remore repository */
    private String defaultLabel;
    
    public JGitBean()
    {
        // TODO Auto-generated constructor stub
    }
    
    public String getUri()
    {
        return uri;
    }
    
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    
    public File getBasedir()
    {
        return basedir;
    }
    
    public void setBasedir(File basedir)
    {
        this.basedir = basedir;
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
    
    public String getDefaultLabel()
    {
        return defaultLabel;
    }
    
    public void setDefaultLabel(String defaultLabel)
    {
        this.defaultLabel = defaultLabel;
    }
    
    @Override
    public String toString()
    {
        return "JGitBean [uri=" + uri + ", basedir=" + basedir + ", username=" + username + ", password=" + password
                + ", defaultLabel=" + defaultLabel + "]";
    }
}
