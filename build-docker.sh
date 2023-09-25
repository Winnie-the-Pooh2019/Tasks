#!/bin/bash

sudo docker build -t ivanch1ck/tasks .
sudo docker push ivanch1ck/tasks
sudo docker compose up