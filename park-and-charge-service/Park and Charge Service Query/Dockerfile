FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/park_and_charge_service_query-*.jar
COPY ${JAR_FILE} park_and_charge_service_query.jar
ENTRYPOINT ["java","-jar","/park_and_charge_service_query.jar"]