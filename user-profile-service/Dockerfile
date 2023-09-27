FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/user_profile_service-*.jar
COPY ${JAR_FILE} user_profile_service.jar
ENTRYPOINT ["java","-jar","/user_profile_service.jar"]
