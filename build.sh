#!/bin/sh

mvn -B clean package -Pprod,native oc:build oc:push \
          -Dquarkus.native.container-build=true \
          -Dquarkus.container-image.build=true \
          -Djkube.docker.push.username=${QUAY_MANUFACTURING_USERNAME} \
          -Djkube.docker.push.password=${QUAY_MANUFACTURING_PASSWORD} 
          
# docker run --rm --privileged multiarch/qemu-user-static:register --reset
# docker build -t quay.io/qiotmanufacturing/factory-product-line:1.0.0-beta1-aarch64 -f src/main/docker/Dockerfile.native.multiarch .
# docker push quay.io/qiotmanufacturing/factory-product-line:1.0.0-beta1-aarch64