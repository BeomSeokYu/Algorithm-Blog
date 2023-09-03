package com.hihat.blog.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageableImpl implements Pageable {
    private int page;
    private int size;
    private long offset;
    private Sort sort;

    public PageableImpl(Integer page, Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = 2;
        }
        this.page = page;
        this.size = size;
        this.offset = 0L;
        sort = Sort.by("id").descending();
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        this.offset = (long) this.page * this.size;
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        this.page++;
        return this;
    }

    @Override
    public Pageable previousOrFirst() {
        this.page--;
        if (this.page < 0) {
            this.page = 0;
        }
        return null;
    }

    @Override
    public Pageable first() {
        this.page = 0;
        return this;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        this.page = pageNumber;
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
