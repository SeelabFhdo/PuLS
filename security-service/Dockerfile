FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/security_service-*.jar
COPY ${JAR_FILE} security_service.jar
ENTRYPOINT ["java","-jar","/security_service.jar"]