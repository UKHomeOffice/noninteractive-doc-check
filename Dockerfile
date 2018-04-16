FROM quay.io/ukhomeofficedigital/openjdk8:latest

VOLUME /tmp
RUN mkdir /app

COPY bin/run.sh /app
COPY target/noninteractive-doc-check.jar /app

EXPOSE 8080
CMD /app/run.sh
