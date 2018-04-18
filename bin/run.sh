#!/usr/bin/env bash

java ${JAVA_OPTS} -Dspring.profiles.active=ssl -Djava.security.egd=file:/dev/./urandom -jar /app/noninteractive-doc-check.jar