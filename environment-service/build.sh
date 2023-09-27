#!/usr/bin/env bash

# You need at least Java 11 to compile the services
mvn clean install

cd "Environment Service Command" && docker build . -t puls/environment-service-command
cd "../Environment Service Query" && docker build . -t puls/environment-service-query
