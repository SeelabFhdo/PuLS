FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/environment_service_query-*.jar
COPY ${JAR_FILE} environment_service_query.jar
ENTRYPOINT ["java","-jar","/environment_service_query.jar"]