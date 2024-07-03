FROM openjdk:17-jdk-alpine
EXPOSE 9595
ADD target/contact_manager.jar contact_manager.jar
ENTRYPOINT ["java","-jar","/contact_manager.jar"]


FROM jenkins/jenkins:lts
USER root
RUN apt-get update && apt-get install -y docker.io
USER jenkins