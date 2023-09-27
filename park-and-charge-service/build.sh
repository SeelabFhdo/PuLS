#!/usr/bin/env bash

# You need at least Java 11 to compile the services
mvn clean install

cd "Park and Charge Service Command" && docker build . -t puls/park-and-charge-service-command
cd "../Park and Charge Service Query" && docker build . -t puls/park-and-charge-service-query
