#!/bin/sh

echo "Factory - Product Line Service"
./mvnw clean package -U -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t quay.io/qiotmanufacturing/factory-product-line:1.0.0-alpha7 .
docker push quay.io/qiotmanufacturing/factory-product-line:1.0.0-alpha7