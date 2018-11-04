package com.git.config;

public interface JGitRepository
{
    String findOne(String filePath) throws Exception;
    
    String getDefaultLabel();
    
    boolean fetchRepos();
    
    boolean pullRepos();
}
