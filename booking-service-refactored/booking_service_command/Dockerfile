FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/booking_service_command-*.jar
COPY ${JAR_FILE} booking_service_command.jar
ENTRYPOINT ["java","-jar","/booking_service_command.jar"]