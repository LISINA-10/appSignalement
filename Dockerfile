FROM openjdk:17
VolUME /tmp
ADD target/appSignalement.jar appSignalement.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "appSignalement.jar"]