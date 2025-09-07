package com.gundaero.alley.domain.s3.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.s3.dto.request.S3GetUrlRequestDTO;
import com.gundaero.alley.domain.s3.dto.request.S3RequestDTO;
import com.gundaero.alley.domain.s3.dto.response.S3ResponseDTO;
import com.gundaero.alley.domain.s3.service.S3PresignService;
import com.gundaero.alley.domain.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Duration;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller implements S3ControllerDocs{

    private final S3PresignService presignService;
    private final S3Service s3Service;

    @PostMapping("/presign")
    public ApiResponse<?> presign(@RequestBody S3RequestDTO req) {
        String key = presignService.buildKey(req.type(), req.originalFilename());
        URL url = presignService.presignPut(key, req.contentType(), Duration.ofMinutes(5));

        S3ResponseDTO dto = new S3ResponseDTO(url.toString(), key);
        return ApiResponse.success("presign", dto);
    }

    @PostMapping("/view-url")
    public ApiResponse<?> viewUrl(@RequestBody S3GetUrlRequestDTO req) {
        String url = s3Service.presignedGetUrl(req.key(), Duration.ofHours(1));
        return ApiResponse.success("viewUrl", url);
    }
}

