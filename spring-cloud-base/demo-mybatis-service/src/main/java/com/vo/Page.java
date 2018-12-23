package com.vo;

import java.util.List;

public class Page<T>
{
    private static final int DEFAULT_CUR_PAGE = 0;
    
    private static final int DEFAULT_PAGE_SIZE = 5;
    
    // -------------需要下发参数----------------
    private Integer curPage;// 当前页（重0开始。也是说 第一页 是 0）
    
    private Integer pageSize;// 每页显示多少条
    
    // -------------需要重DB查询的数据-----------------
    
    private Long totalCount;// 一共有多少条数据
    
    private List<T> content;// 内容列表
    
    // -------------需要计算的数据--------------
    private Long totalPage;// 一共有多少页
    
    private Integer curCount;// 当前页 有多少数据
    
    private Boolean isFirst; // 是否是第一页
    
    private Boolean isLast;// 是否是最后一页
    
    private Integer offset;// 分页的偏移量
    
    public Page()
    {
    }
    
    public Page(Integer curPage, Integer pageSize)
    {
        this.curPage = curPage;
        this.pageSize = pageSize;
    }
    
    public Integer getOffset()
    {
        offset = this.getCurPage() * this.getPageSize();
        return offset;
    }
    
    public Integer getCurCount()
    {
        return null == content ? 0 : content.size();
    }
    
    public Long getTotalPage()
    {
        Long temp = this.getTotalCount() / this.getPageSize();
        
        totalPage = this.getTotalCount() % this.getPageSize() == 0 ? temp : temp + 1;
        
        return totalPage;
    }
    
    public Boolean getIsFirst()
    {
        isFirst = this.getCurPage() > 0 ? false : true;
        
        return isFirst;
    }
    
    public Boolean getIsLast()
    {
        isLast = this.getCurPage() < (getTotalPage() - 1) ? false : true;
        
        return isLast;
    }
    
    public Integer getCurPage()
    {
        if (null == this.curPage || this.curPage < 0)
        {
            this.curPage = DEFAULT_CUR_PAGE;
        }
        
        return curPage;
    }
    
    public Integer getPageSize()
    {
        if (null == this.pageSize || this.pageSize < 0)
        {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        
        return pageSize;
    }
    
    public Long getTotalCount()
    {
        if (null == this.totalCount)
        {
            this.totalCount = 0L;
        }
        
        return totalCount;
    }
    
    public void setCurPage(Integer curPage)
    {
        this.curPage = curPage;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public void setTotalCount(Long totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public List<T> getContent()
    {
        return content;
    }
    
    public void setContent(List<T> content)
    {
        this.content = content;
    }
    
    public void setTotalPage(Long totalPage)
    {
        this.totalPage = totalPage;
    }
    
    public void setCurCount(Integer curCount)
    {
        this.curCount = curCount;
    }
    
    public void setIsFirst(Boolean isFirst)
    {
        this.isFirst = isFirst;
    }
    
    public void setIsLast(Boolean isLast)
    {
        this.isLast = isLast;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    @Override
    public String toString()
    {
        return "Page [curPage=" + curPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount + ", content="
                + content + ", totalPage=" + totalPage + ", curCount=" + curCount + ", isFirst=" + isFirst + ", isLast="
                + isLast + "]";
    }
    
}
