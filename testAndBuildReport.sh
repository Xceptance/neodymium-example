#!/bin/sh

mkdir -p ./allure-results
mkdir -p ./target/allure-results

if [ -d ./target/site/allure-maven-plugin/history ]; then
  if [ -d ./target/allure-results/history ]; then
    rm -rf ./target/allure-results/history
  fi
  mv ./target/site/allure-maven-plugin/history ./target/allure-results/history
  cp -r ./target/allure-results/history ./allure-results
fi

mvn test allure:report