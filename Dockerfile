FROM openjdk:15
ADD target/docker-userservice.jar docker-userservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-userservice.jar"]