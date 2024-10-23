package com.tma.demo.service.report;

import com.tma.demo.dto.request.ReportPostRequest;
import org.springframework.stereotype.Service;

/**
 * ReportService
 * Version 1.0
 * Date: 16/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 16/10/2024        NGUYEN             create
 */
public interface ReportService {
    void report(ReportPostRequest reportPostRequest);
}
