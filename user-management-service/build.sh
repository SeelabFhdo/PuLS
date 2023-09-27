#!/usr/bin/env bash

# You need at least Java 11 to compile the services
mvn clean install

cd "User Management Service Command" && docker build . -t puls/user-management-service-command
cd "../User Management Service Query" && docker build . -t puls/user-management-service-query
