package com.git.config;

public interface JGitRepository
{
    String findOne(String fileNamePath) throws Exception;
    
    // String getDefaultLabel();
    // boolean fetchRepos();
    // boolean pullRepos(String label);
}
