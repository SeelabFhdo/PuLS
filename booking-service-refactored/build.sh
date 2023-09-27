#!/usr/bin/env bash

# You need at least Java 11 to compile the services
mvn clean install

cd "booking_service_command" && docker build . -t puls/booking-service-command
cd "../booking_service_query" && docker build . -t puls/booking-service-query
