#!/usr/bin/env bash

# You need at least Java 11 to compile the services
ng build

docker build . -t puls/uiservice
