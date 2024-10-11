package com.tma.demo.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tma.demo.common.ErrorCode;
import com.tma.demo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * CloudinaryService
 * Version 1.0
 * Date: 10/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 10/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map upload(MultipartFile file, String folderName, String id)  {
        try{
            return this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", id,
                    "folder", folderName,
                    "overwrite", true));
        }catch (IOException io){
            throw new BaseException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }

}
