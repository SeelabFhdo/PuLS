FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/user_management_service_query-*.jar
COPY ${JAR_FILE} user_management_service_query.jar
ENTRYPOINT ["java","-jar","/user_management_service_query.jar"]