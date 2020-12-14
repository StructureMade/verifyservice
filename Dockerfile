FROM openjdk:15
ADD target/verifyservice.jar verifyservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "verifyservice.jar"]