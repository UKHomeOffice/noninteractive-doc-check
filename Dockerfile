FROM quay.io/ukhomeofficedigital/openjdk8:latest

VOLUME /tmp
RUN mkdir /app
RUN groupadd -g 5002 www && \
    useradd -u 5002 -g 5002 www

COPY bin/run.sh /app
COPY target/noninteractive-doc-check.jar /app

EXPOSE 8080
USER 5002
CMD /app/run.sh
