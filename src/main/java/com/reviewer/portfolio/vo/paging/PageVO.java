package com.reviewer.portfolio.vo.paging;

import lombok.Data;

@Data
public class PageVO {
	private int totalCnt;
    private int displayPageNum = 5;

    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    private Criteria criteria;

    public void setTotalCnt(int totalCnt){
        this.totalCnt = totalCnt;
        calcPage();
    }

    public void calcPage(){
        startPage = (criteria.getPage() - 1) / (displayPageNum * displayPageNum) + 1;
        endPage =  startPage + displayPageNum - 1;

        int totalPage = 0; 
        if (totalCnt == 0) {
        	totalPage = 1;
        } else {
        	totalPage = (int) Math.ceil((totalCnt / (double)criteria.getCntPerPage()));
        }
        
        if (endPage > totalPage) {
        	endPage = totalPage;
        } 
        prev = startPage == 1 ? false : true;
        next = endPage * criteria.getCntPerPage() >= totalCnt ? false : true;
    }
}
