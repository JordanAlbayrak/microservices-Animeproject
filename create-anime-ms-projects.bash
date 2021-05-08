#!/usr/bin/env bash

mkdir microservices
cd microservices

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=anime-service \
--package-name=com.albayrak.microservices.core.anime \
--groupId=com.albayrak.microservices.core.anime \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
anime-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=anime-review-service \
--package-name=com.albayrak.microservices.core.anime-review \
--groupId=com.albayrak.microservices.core.anime-review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
anime-review-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=anime-recommendation-service \
--package-name=com.albayrak.microservices.core.anime-recommendation \
--groupId=com.albayrak.microservices.core.anime-recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
anime-recommendation-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=anime-composite-service \
--package-name=com.albayrak.microservices.composite.anime \
--groupId=com.albayrak.microservices.composite.anime \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
anime-composite-service

cd ..
