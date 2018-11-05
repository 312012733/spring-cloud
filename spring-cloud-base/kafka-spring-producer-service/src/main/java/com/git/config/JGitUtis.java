package com.git.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.IOUtils;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class JGitUtis implements JGitRepository
{
    private static final Logger LOG = LoggerFactory.getLogger(JGitUtis.class);
    
    private JGitBean jGitBean;
    
    public JGitUtis(JGitBean jGitBean) throws Exception
    {
        this.jGitBean = jGitBean;
        
        init(jGitBean.getUri(), jGitBean.getBasedir());
    }
    
    private void init(String uri, File baseDir) throws Exception
    {
        LOG.info("【init git start. {}】", JSONObject.toJSONString(jGitBean));
        File gitFile = gitFile(baseDir);
        
        if (gitFile.exists())
        {
            try
            {
                Git git = openGit(baseDir);
                this.pull(git);
                
                LOG.info("【git pull success. {}】", JSONObject.toJSONString(jGitBean));
            }
            catch (Exception e)
            {
                LOG.warn("pull error. case:{}. try clone agin..", e.getMessage());
                
                cloneRepository(uri, baseDir);
                LOG.info("【git clone success. {}】", JSONObject.toJSONString(jGitBean));
            }
        }
        else
        {
            cloneRepository(uri, baseDir);
            
            LOG.info("【git clone success. {}】", JSONObject.toJSONString(jGitBean));
        }
        
    }
    
    private void cloneRepository(String uri, File baseDir) throws Exception
    {
        deleteFolder(baseDir);
        Git.cloneRepository().setURI(uri).setDirectory(baseDir).call();
    }
    
    private void pull(Git git) throws Exception
    {
        git.pull().call();
    }
    
    private void fetch(Git git) throws Exception
    {
        git.fetch().call();
    }
    
    private File gitFile(File baseDir)
    {
        File gitFile = new File(baseDir, ".git");
        return gitFile;
    }
    
    private Git openGit(File baseDir) throws IOException
    {
        File gitFile = gitFile(baseDir);
        Git git = Git.open(gitFile);
        
        return git;
    }
    
    private void deleteFolder(File file)
    {
        if (!file.exists())
        {
            return;
        }
        
        if (file.isFile() || file.list().length == 0)
        {
            file.delete();
        }
        else
        {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                deleteFolder(files[i]);
                files[i].delete();
            }
        }
    }
    
    @Override
    public String findOne(String filePath) throws Exception
    {
        String file = jGitBean.getBasedir() + File.separator + filePath;
        // Resource resource = resourceLoader.getResource(file);
        byte[] byteArray = IOUtils.toByteArray(new FileInputStream(file));
        
        return new String(byteArray, "utf-8");
    }
    
    @Override
    public String getDefaultLabel()
    {
        return jGitBean.getDefaultLabel();
    }
    
    @Override
    public boolean fetchRepos()
    {
        try
        {
            Git git = this.openGit(this.jGitBean.getBasedir());
            
            this.fetch(git);
            
            return true;
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
        
        return false;
    }
    
    @Override
    public boolean pullRepos()
    {
        try
        {
            Git git = this.openGit(this.jGitBean.getBasedir());
            this.pull(git);
            
            return true;
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
        
        return false;
    }
    
    // public static void main(String[] args) throws Exception
    // {
    // JGitBean jGitBean = new JGitBean();
    //
    // jGitBean.setBasedir(new File("F:\\temp\\local-config-center"));
    // jGitBean.setUri("https://github.com/312012733/config-center.git");
    //
    // JGitRepository JGitRepository = new JGitUtis(jGitBean);
    //
    // boolean pullRepos = JGitRepository.pullRepos();
    //
    // boolean fetchRepos = JGitRepository.fetchRepos();
    //
    // String findOne = JGitRepository.findOne("readme.txt");
    // System.out.println(findOne);
    //
    // }
}
