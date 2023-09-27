FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/parkingspace_search_service-*.jar
COPY ${JAR_FILE} parkingspace_search_service.jar
ENTRYPOINT ["java","-jar","/parkingspace_search_service.jar"]
