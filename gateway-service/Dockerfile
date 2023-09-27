FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/gateway_service-*.jar
COPY ${JAR_FILE} gateway_service.jar
ENTRYPOINT ["java","-jar","/gateway_service.jar"]