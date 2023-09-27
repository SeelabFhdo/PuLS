FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/chargecloud-data-service-*.jar
COPY ${JAR_FILE} chargecloud-data-service.jar
ENTRYPOINT ["java","-jar","/chargecloud-data-service.jar"]
