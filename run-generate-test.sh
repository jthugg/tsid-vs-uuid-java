#!/bin/bash

for i in {1..10}; do
  echo "$i 회차 실행..."
  echo "test$i,256,512,1_024,4_096,8_192,100_000,300_000,500_000" >> src/test/resources/results/generate.csv
  echo -n "tsid" >> src/test/resources/results/generate.csv
  ./gradlew test --tests "org.example.benchmark.TsidGenerateTest"
  echo -e -n "\nuuid" >> src/test/resources/results/generate.csv
  ./gradlew test --tests "org.example.benchmark.UuidGenerateTest"
  echo -e "\n" >> src/test/resources/results/generate.csv
done
