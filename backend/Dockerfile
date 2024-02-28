FROM gradle:8.6.0-jdk21 AS build
WORKDIR /home/gradle/project
COPY ./* /home/gradle/project
RUN ./gradlew build -x test

FROM eclipse-temurin:21.0.2_13-jre AS run
ENV WORKDIR /Backend/
COPY --from=build /home/gradle/project/build/libs/Backend.jar $WORKDIR

RUN groupadd -r Backend && useradd --no-log-init -r -g Backend Backend
USER Backend

WORKDIR $WORKDIR

ENTRYPOINT ["java", "-jar", "Backend.jar"]