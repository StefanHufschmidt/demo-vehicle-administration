FROM gradle:8.6.0-jdk21 AS installdeps
WORKDIR /home/gradle/project
COPY ./build.gradle.kts /home/gradle/project
COPY ./settings.gradle.kts /home/gradle/project
COPY ./gradlew /home/gradle/project
COPY ./gradlew.bat /home/gradle/project
COPY ./gradle /home/gradle/project
RUN gradle dependencies

FROM installdeps AS build
COPY . /home/gradle/project
RUN gradle build -x test

FROM eclipse-temurin:21.0.2_13-jre AS run
ENV WORKDIR /Backend/
COPY --from=build /home/gradle/project/build/libs/Backend.jar $WORKDIR

RUN groupadd -r Backend && useradd --no-log-init -r -g Backend Backend
USER Backend

WORKDIR $WORKDIR

ENTRYPOINT ["java", "-jar", "Backend.jar"]