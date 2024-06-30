FROM openjdk:17-jdk-alpine
EXPOSE 9595
ADD target/*.jar contact_manager.jar
ENTRYPOINT ["java","-jar","/contact_manager.jar"]