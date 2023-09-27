####################
## Stage 1: Build ##
####################
FROM maven:3.6-jdk-11-slim as builder
WORKDIR /build

## Build Step 1: Cache dependencies
COPY pom.xml .
COPY ci_settings.xml .
ARG CI_JOB_TOKEN
RUN mvn -s ci_settings.xml dependency:go-offline

## Build Step 2: Actual build
COPY src/ /build/src/
RUN mvn -s ci_settings.xml clean package

##################
## Stage 2: Run ##
##################
FROM openjdk:11-jre-slim
ARG APP_HOME=/usr/apps
ENV APP_FILE app.jar

## Don't use the system user
RUN adduser --system --no-create-home --group spring

COPY --from=builder /build/target/*.jar $APP_HOME/$APP_FILE
WORKDIR $APP_HOME

RUN chown spring:spring $APP_HOME
RUN chmod 755 $APP_HOME

USER spring:spring

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]