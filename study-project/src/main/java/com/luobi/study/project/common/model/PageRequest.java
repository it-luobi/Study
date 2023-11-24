package com.luobi.study.project.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

    private static final int DEFAULT_PAGE_SIZE = 15;

    @Min(value = 1)
    private int pageNo = 1;

    @Min(value = 1)
    private int pageSize = DEFAULT_PAGE_SIZE;

    private boolean searchAll = false;

    public PageRequest(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageRequest(boolean searchAll) {
        this.searchAll = searchAll;
    }

    public int getPageNo() {
        if (searchAll) {
            return 1;
        }
        return pageNo;
    }

    public int getPageSize() {
        if (searchAll) {
            return Integer.MAX_VALUE;
        }
        return pageSize;
    }

    public int getOffset() {
        if (searchAll) {
            return 0;
        }
        return (pageNo - 1) * pageSize;
    }

    public int getLimit() {
        if (searchAll) {
            return Integer.MAX_VALUE;
        }
        return pageSize;
    }

}
