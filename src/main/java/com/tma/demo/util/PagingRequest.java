package com.tma.demo.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * PageRequest
 * Version 1.0
 * Date: 17/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 17/10/2024        NGUYEN             create
 */
@Data
@Builder
public class PagingRequest<T> {
    private int page;
    private int size;
    private String sortField;
    private Sort.Direction sortBy;
    private T filter;

    public static <T> PageRequest getPageRequest(PagingRequest<T> requestPaging) {
        return StringUtils.isEmpty(requestPaging.getSortField())
                ? PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize())
                : PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize(),
                Sort.by(requestPaging.getSortBy(), new String[]{requestPaging.getSortField()}));
    }
}
