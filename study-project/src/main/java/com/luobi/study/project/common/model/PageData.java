package com.luobi.study.project.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {

    private int total;
    private int pageSize;
    private int pageNo;
    private List<T> data;

    public PageData() {
        this(Lists.newArrayList());
    }

    public PageData(PageRequest pageRequest) {
        this(Lists.newArrayList(), pageRequest, 0);
    }

    public PageData(List<T> data, PageRequest pageRequest, int total) {
        this.data = data;
        this.pageNo = getPageNo(pageRequest);
        this.pageSize = getPageSize(pageRequest);
        this.total = total;
    }

    public PageData(List<T> data, int pageNo, int pageSize, int total) {
        this.data = data;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    public PageData(List<T> data) {
        this(data, null, null == data ? 0 : data.size());
    }

    public PageData(int pageNo, int pageSize) {
        super();
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public PageRequest getPageRequest() {
        return new PageRequest(pageNo, pageSize);
    }

    private int getPageNo(PageRequest pageRequest) {
        return pageRequest == null ? 1 : pageRequest.getPageNo();
    }

    private int getPageSize(PageRequest pageRequest) {
        return pageRequest == null ? Integer.MAX_VALUE : pageRequest.getPageSize();
    }

    @Deprecated
    public PageData total(int total) {
        this.total = total;
        return this;
    }

    @Deprecated
    public PageData data(List<T> data) {
        this.data = data;
        return this;
    }

}
