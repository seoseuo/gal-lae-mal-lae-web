# 1단계: 빌드용 이미지
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

# 2단계: 실행용 경량 이미지
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
