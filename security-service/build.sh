#!/usr/bin/env bash

# You need at least Java 11 to compile the services
mvn clean install

docker build . -t puls/security-service