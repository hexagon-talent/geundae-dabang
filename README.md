# 근대로

<p align="center">
  <img src="images/logo.png" width="150"/>
</p>

<p align="center">
  근대에서 오늘로 이어지는 특별한 지도 <strong>근대로</strong>
</p>

<p align="center">
  <a href="https://m.onestore.co.kr/v2/ko-kr/app/0001002384">
    👉 근대로 바로가기
  </a>
  <img alt="Version" src="https://img.shields.io/badge/version-1.2.1-blue.svg?cacheSeconds=2592000" />
</p>

---
## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [프로젝트 소개](#프로젝트-소개)
3. [주요 화면 및 기능 소개](#주요-화면-및-기능-소개)
4. [기술 스택](#기술-스택)
5. [시스템 아키텍처](#시스템-아키텍처)
6. [ERD](#erd-entity-relationship-diagram)
7. [성과](#성과)
8. [팀원](#팀원)

---

## 프로젝트 개요

- **서비스명**: 근대로
- **팀명**: 6각형인재
- **개발 기간**: 2025.09.01 ~ 2025.09.30 (4주)  
- **개발 인원**: 3명 (Backend 1명, Infra 1명, Android 1명)

---

## 프로젝트 소개

<p align="center">
  <img src="images/banner3.png" width="400"/>
</p>

<p align="center">
  <img src="images/info1.png" width="100" style="margin-right:8px; border-radius:6px;">
  <img src="images/info2.png" width="100" style="margin-right:8px; border-radius:6px;">
  <img src="images/info3.png" width="100" style="margin-right:8px; border-radius:6px;">
  <img src="images/info4.png" width="100" style="margin-right:8px; border-radius:6px;">
  <img src="images/info5.png" width="100" style="margin-right:8px; border-radius:6px;">
  <img src="images/info6.png" width="100" style="border-radius:6px;">
</p>

'근대로'는 대구 근대문화골목을 **GPS 기반 사진 미션**으로 탐방하고 기록하는 근대 골목 투어 앱입니다.

카카오 지도 API로 근대 골목 코스 내의 **12개 주요 장소**를 확인하고, 각 지점에서 **사진 미션**을 수행하면 완료한 기록이 **스탬프 형태**로 저장되어 탐방 추억을 한눈에 볼 수 있습니다.

공공데이터 API를 통해 장소의 **역사 설명, 근처 식당, 행사 정보**를 앱 내에서 바로 제공하여
**탐색-체험-기록**이 하나의 앱에서 자연스럽게 이어집니다.


---

## 주요 화면 및 기능 소개

### 1. 홈 화면
> 마스코트 ‘꾸미’가 미션 안내 메시지를 전달하고, 진행 상황을 한눈에 표시합니다.

<p align="left">
  <img src="images/main1.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/main2.jpg" width="200" style="border-radius:8px;">
</p>

---

### 2. 로그인 화면
> 카카오 로그인으로 손쉽게 시작합니다.

<p align="left">
  <video src="images/login1.mp4" width="200" style="border-radius:8px;" autoplay loop muted playsinline></video>
</p>

---

### 3. 지도 탭
> 카카오 지도 API 기반으로 근대문화골목 주요 장소를 표시하고, 사진 미션과 장소 설명을 제공합니다.

<p align="left">
  <img src="images/map1.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/map2.jpg" width="200" style="border-radius:8px;">
</p>

<p align="left">
  <img src="images/map3.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/map4.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/map5.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/map6.jpg" width="200" style="border-radius:8px;">
</p>

---

### 4. 근처 볼거리 탭
> 현재 위치 기반으로 근처 행사·맛집을 추천합니다.

<p align="left">
  <img src="images/tour1.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/tour2.jpg" width="200" style="border-radius:8px;">
</p>

---

### 5. 기록 탭
> 미션 수행 후 스탬프를 터치해 인증샷을 확인할 수 있습니다. 모든 미션을 완료하면 ‘대구 근대 여행 완료’ 엽서를 받을 수 있습니다.

<p align="left">
  <img src="images/stamp1.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/stamp2.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/stamp3.jpg" width="200" style="border-radius:8px;">
</p>

<p align="left">
  <img src="images/stamp4.jpg" width="200" style="margin-right:10px; border-radius:8px;">
  <img src="images/stamp5.jpg" width="200" style="border-radius:8px;">
</p>


---
## 기술 스택

#### Android
<p>
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84?style=flat-square&logo=androidstudio&logoColor=white"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white"/>
</p>

#### Backend
<p>
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
    <img src="https://img.shields.io/badge/MyBatis-000000?style=flat-square&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white"/>
    <img src="https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens&logoColor=white"/>
    <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white"/>
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white"/>
</p>

#### Infra
<p>
  <img src="https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white"/>
  <img src="https://img.shields.io/badge/-AWS EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/>
  <img src="https://img.shields.io/badge/AWS_S3-569A31?logo=amazons3&logoColor=fff&style=flat-square"/>
  <img src="https://img.shields.io/badge/-Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/>    
  <img src="https://img.shields.io/badge/Docker_Compose-2496ED?style=flat&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/-Nginx-269539?style=flat-square&logo=Nginx&logoColor=white"/>

</p>

#### Tools
<p>
  <img src="https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=figma&logoColor=white"/>
  <img src="https://img.shields.io/badge/git-%23F05033.svg?style=flat-square&logo=git&logoColor=white"/>
  <img src="https://img.shields.io/badge/-Notion-000000?style=flat-square&logo=notion&logoColor=white"/>
</p>

## 시스템 아키텍처


---

## ERD (Entity Relationship Diagram)

---

## 성과
 <a href="https://m.onestore.co.kr/v2/ko-kr/app/0001002384">
    원스토어에 어플 출시
  </a>

<br>

<p align="left">
  <img src="images/onestore.png" width="200" style="margin-right:10px; border-radius:8px;">
</p>

## 팀원

| **손은주** | **제갈민** | **박성민** |
|------------------|------------|------------|
| Infra            | Backend    | Android    |



