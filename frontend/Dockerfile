# 1) Build stage: Vite로 정적 파일 빌드
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .

RUN npm run build

# 2) Serve stage: Nginx로 정적 파일 서빙
FROM nginx:1.27-alpine
# 커스텀 설정(아래 nginx.conf)을 기본 서버 설정으로 사용
COPY nginx.conf /etc/nginx/conf.d/default.conf
# 빌드 산출물 복사
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
# 컨테이너 헬스체크용 엔드포인트(/health) 응답 확인
HEALTHCHECK --interval=30s --timeout=3s --retries=3 CMD wget -qO- http://127.0.0.1/health || exit 1
