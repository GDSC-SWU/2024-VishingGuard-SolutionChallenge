# 기본 이미지 설정. OpenJDK 17을 기반으로 함
FROM openjdk:17-jdk-slim

# 애플리케이션 파일을 이미지로 복사
ARG JAR_FILE=target/*.jar
COPY ./build/libs/Back-End-0.0.1-SNAPSHOT.jar app.jar

# 포트 설정
EXPOSE 8080

# 컨테이너 시작 시 실행될 명령 설정
ENTRYPOINT ["java", "-jar", "/app.jar"]