FROM gradle:7.2.0-jdk17 as builder
WORKDIR /project
COPY build.gradle ./
ADD src ./src
RUN gradle build --stacktrace

FROM eclipse-temurin:17_35-jdk
WORKDIR /app
COPY --from=builder project/build/libs/app.jar ./app.jar

ENTRYPOINT [ "sh", "-c", "java -jar app.jar" ]
