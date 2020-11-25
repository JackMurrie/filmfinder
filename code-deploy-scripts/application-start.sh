#!/bin/bash

echo "Application Start" >> /home/ubuntu/log.txt

cd /home/ubuntu/film-finder/backend
mvn clean package -DskipTests
mvn exec:java > /dev/null 2> /dev/null < /dev/null &

cd ..
npm start > /dev/null 2> /dev/null < /dev/null &