FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/environment_service_command-*.jar
COPY ${JAR_FILE} environment_service_command.jar
ENTRYPOINT ["java","-jar","/environment_service_command.jar"]