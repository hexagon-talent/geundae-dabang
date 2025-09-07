package com.gundaero.alley.domain.s3.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.s3.dto.request.S3GetUrlRequestDTO;
import com.gundaero.alley.domain.s3.dto.request.S3RequestDTO;
import com.gundaero.alley.domain.s3.dto.response.S3ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface S3ControllerDocs {

    @Operation(
            summary = "Presigned PUT URL 발급 (클라이언트 직접 업로드용)",
            description =
                    """
                    클라이언트가 S3에 직접 업로드할 수 있도록 Presigned PUT URL을 발급합니다.

                    1️⃣ 동작  
                    - 서버가 업로드용 S3 key를 생성하고, 해당 key에 대한 Presigned PUT URL을 반환합니다.  
                    - 클라이언트는 반환받은 `uploadUrl` 에 파일을 HTTP PUT 으로 업로드합니다.  
                    - 이때 `Content-Type` 헤더는 presign 요청 시의 값과 동일해야 합니다(불일치 시 403).

                    2️⃣ 요청 Body
                    ```json
                    {
                      "type": "location-photo",
                      "originalFilename": "IMG_0001.jpg",
                      "contentType": "image/jpeg"
                    }
                    ```

                    3️⃣ 응답 예시
                    ```json
                    {
                      "code": "0",
                      "message": "정상 처리 되었습니다.",
                      "data": {
                        "presign": {
                          "uploadUrl": "https://<bucket>.s3....X-Amz-Signature=...",
                          "key": "location-photo/2e3f5c4e-..._IMG_0001.jpg"
                        }
                      }
                    }
                    ```

                    ✅ 업로드 성공 후에는 `/api/photos/complete` 로 `{ locationId, key }` 를 보내 DB에 저장(업서트).  
                    """
    )
    ApiResponse<?> presign(@RequestBody S3RequestDTO req);


    @Operation(
            summary = "Presigned GET URL 발급 (보기용)",
            description =
                    """
                    S3에 저장된 객체의 key를 입력받아, 제한시간 동안 유효한 Presigned GET URL을 반환합니다.

                    1️⃣ 동작
                    - 입력받은 `key` 에 대해 일정 시간(예: 1시간)만 유효한 다운로드 URL을 발급합니다.
                    - 클라이언트는 이 URL을 이미지 로더(Glide/Coil/<img>)에 그대로 사용하면 됩니다.

                    2️⃣ 요청 Body
                    ```json
                    { "key": "location-photo/2e3f5c4e-..._IMG_0001.jpg" }
                    ```

                    3️⃣ 응답 예시
                    ```json
                    {
                      "code": "0",
                      "message": "정상 처리 되었습니다.",
                      "data": {
                        "viewUrl": "https://<bucket>.s3....X-Amz-Signature=..."
                      }
                    }
                    ```
                    """
    )
    ApiResponse<?> viewUrl(@RequestBody S3GetUrlRequestDTO req);
}
