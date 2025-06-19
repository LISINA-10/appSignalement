FROM openjdk:17
WORKDIR /app
ARG JAR_FILE=target/appSignalement.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]