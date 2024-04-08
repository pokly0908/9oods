FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/*.jar
ENV SPRING_PROFILES_ACTIVE=dev
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
