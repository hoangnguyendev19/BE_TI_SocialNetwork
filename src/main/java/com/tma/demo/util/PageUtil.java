package com.tma.demo.util;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.constant.CommonConstant;
import com.tma.demo.exception.BaseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PageUtil
 * Version 1.0
 * Date: 17/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 17/10/2024        NGUYEN             create
 */
public class PageUtil {
    public static <T> PageRequest getPageRequest(PagingRequest<T> requestPaging) {
        return requestPaging.getOrderList().isEmpty()
                ? PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize())
                : PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize(),
                Sort.by(requestPaging.getOrderList()));
    }

    public static List<Sort.Order> getOrderList(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(CommonConstant.COMMA)){
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(CommonConstant.COMMA);
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        }
        else orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        return orders;
    }

    public static Sort.Direction getSortDirection(String s) {
        Sort.Direction direction = null;
        try {
            direction = Sort.Direction.fromString(s);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.DIRECTION_INVALID);
        }
        return direction;
    }

    public static Pageable getPageable(PagingRequest pagingRequest) {
        return getPageRequest(pagingRequest);
    }
}
