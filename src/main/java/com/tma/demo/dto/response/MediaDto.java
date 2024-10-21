package com.tma.demo.dto.response;

import com.tma.demo.common.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MediaDto
 * Version 1.0
 * Date: 11/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaDto {
    private String id;
    private MediaType mediaType;
    private String url;

}
