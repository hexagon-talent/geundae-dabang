package com.gundaero.alley.domain.photo.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.auth.entity.CustomUserDetail;
import com.gundaero.alley.domain.photo.dto.request.PhotoCompleteRequestDTO;
import com.gundaero.alley.domain.photo.dto.request.PhotoUploadRequestDTO;
import com.gundaero.alley.domain.photo.dto.response.PhotoCompleteResponseDTO;
import com.gundaero.alley.domain.photo.dto.response.PhotoListItemDTO;
import com.gundaero.alley.domain.photo.dto.response.PhotoUploadResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PhotoControllerDocs {

    @Operation(
            summary = "Presigned PUT URL 발급 (클라이언트 직접 업로드용)",
            description =
                    """
                    사용자 사진 업로드를 위해 S3 Presigned PUT URL을 발급합니다.

                    1️⃣ 동작
                    - 서버가 업로드용 S3 key(`photos/{userId}/{locationId}/{uuid}_{originalFilename}`)를 생성합니다.
                    - 해당 key에 대한 Presigned PUT URL을 반환합니다.
                    - 클라이언트는 반환받은 `uploadUrl` 로 HTTP PUT 업로드를 수행합니다.
                    - 업로드 시 `Content-Type` 헤더는 요청의 `contentType` 과 **동일**해야 합니다(불일치 시 403).

                    2️⃣ 요청 Body
                    ```json
                    {
                      "locationId": 123,
                      "originalFilename": "IMG_0001.png",
                      "contentType": "image/png"
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
                          "key": "photos/42/123/2e3f5c4e-..._IMG_0001.png"
                        }
                      }
                    }
                    ```
                    """
    )
    ResponseEntity<ApiResponse<PhotoUploadResponseDTO>> uploadPresign(
            @AuthenticationPrincipal CustomUserDetail me,
            @RequestBody PhotoUploadRequestDTO req
    );

    @Operation(
            summary = "업로드 완료 처리 (DB UPSERT)",
            description =
                    """
                    S3 업로드 완료 후, 생성된 객체 `key` 를 사용자-장소 매핑 테이블에 업서트(UPSERT) 합니다.

                    1️⃣ 동작
                    - 입력받은 `locationId`, `key` 를 기준으로 `user_tour_location` 등 연관 테이블에 사진 정보를 저장/갱신합니다.
                    - 동일 사용자/장소에 대해 기존 기록이 있으면 갱신, 없으면 신규 삽입합니다.

                    2️⃣ 요청 Body
                    ```json
                    {
                      "locationId": 123,
                      "key": "photos/42/123/2e3f5c4e-..._IMG_0001.png"
                    }
                    ```

                    3️⃣ 응답 예시
                    ```json
                    {
                      "code": "0",
                      "message": "정상 처리 되었습니다.",
                      "data": {
                        "photo": {
                          "id": 10,
                          "locationId": 123,
                          "key": "photos/42/123/2e3f5c4e-..._IMG_0001.png"
                        }
                      }
                    }
                    ```
                    """
    )
    ResponseEntity<ApiResponse<PhotoCompleteResponseDTO>> complete(
            @AuthenticationPrincipal CustomUserDetail me,
            @RequestBody PhotoCompleteRequestDTO req
    );

    @Operation(
            summary = "내가 업로드한 모든 사진 조회 (locationId, locationName, url)",
            description =
                    """
                    현재 로그인한 사용자가 업로드한 모든 사진을 최신순으로 반환합니다.
                    `url`은 S3 presigned GET URL이라 유효시간 내에서 바로 이미지 표시가 가능합니다.
                    
                    1️⃣ 요청 헤더:
                    - `Authorization: Bearer {accessToken}`
                    
                    2️⃣ 응답 구조 (ApiResponse):
                    ```json
                    {
                      "code": "0",
                      "message": "정상 처리 되었습니다.",
                      "data": {
                        "photos": [
                          {
                            "locationId": 1,
                            "locationName": "청라언덕",
                            "url": "https://gundaero-alley-prod-bucket.s3.ap-northeast-2.amazonaws.com/photos/3/1/....png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=..."
                          },
                          {
                            "locationId": 7,
                            "locationName": "약령시 한방문화체험",
                            "url": "https://gundaero-alley-prod-bucket.s3.ap-northeast-2.amazonaws.com/photos/3/7/....jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=..."
                          }
                        ]
                      }
                    }
                    ```
                    """
    )
    ResponseEntity<ApiResponse<List<PhotoListItemDTO>>> myPhotos(
            @AuthenticationPrincipal CustomUserDetail me
    );
}
