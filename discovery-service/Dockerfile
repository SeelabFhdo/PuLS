FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/discovery_service-*.jar
COPY ${JAR_FILE} discovery_service.jar
ENTRYPOINT ["java","-jar","/discovery_service.jar"]