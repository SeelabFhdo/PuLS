FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/booking_service_query-*.jar
COPY ${JAR_FILE} booking_service_query.jar
ENTRYPOINT ["java","-jar","/booking_service_query.jar"]