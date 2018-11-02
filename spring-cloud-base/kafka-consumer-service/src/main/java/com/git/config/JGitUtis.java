package com.git.config;

import java.io.File;

import org.eclipse.jgit.api.Git;

public class JGitUtis
{
    
    public static void cloneRepository(String uri, File baseDir) throws Exception
    {
        baseDir.delete();
        
        Git.cloneRepository().setURI(uri).setDirectory(baseDir).call();
    }
    
}
