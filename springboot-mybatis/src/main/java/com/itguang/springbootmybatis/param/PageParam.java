package com.itguang.springbootmybatis.param;

/**
 * 分页工具类
 * @author itguang
 * @create 2017-12-08 15:03
 **/
public class PageParam {

    /**
     * 起始行
     */
    private int beginLine;
    /**
     * 默认页大小
     */
    private Integer pageSize = 3;
    /**
     *当前页
     */
    private Integer currentPage=0;

    public int getBeginLine() {
        return pageSize*currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
