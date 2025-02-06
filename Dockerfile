FROM gradle:8-jdk21-corretto AS builder
USER root
WORKDIR /app
ADD . /app
RUN gradle build --stacktrace

FROM amazoncorretto:21
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/build/libs/dag-0.0.1.jar .
COPY /src/main/resources/ged/** .

EXPOSE 5005
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "dag-0.0.1.jar"]