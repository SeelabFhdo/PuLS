FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/user_management_service_command-*.jar
COPY ${JAR_FILE} user_management_service_command.jar
ENTRYPOINT ["java","-jar","/user_management_service_command.jar"]