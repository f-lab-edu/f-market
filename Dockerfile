# 빌드 단계
FROM amazoncorretto:17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 실행 단계
FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./app.jar
COPY ./wait-for-it.sh ./wait-for-it.sh

RUN chmod +x wait-for-it.sh

#ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080