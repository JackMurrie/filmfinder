#!/bin/bash

echo "Stopping Server" >> /home/ubuntu/log.txt

lsof -ti tcp:3000 | xargs kill
lsof -ti tcp:8080 | xargs kill