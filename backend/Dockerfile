
FROM openjdk:8-jdk

LABEL maintainer=Thomas\ Eberle
LABEL last_updated=21/03/2020

WORKDIR /coronatracker

COPY corona-server/target/ ./

EXPOSE 8080

ARG VERSION

RUN mv corona-server/target/wvsvcoronatracker-${VERSION}-server.jar /tmp/corona-server.jar && \
    rm -R /coronatracker/* && \
    mv /tmp/corona-server.jar ./ && \
    chmod 755 corona-server.jar

CMD spring_profiles_active=prod java -Djava.security.egd=file:/dev/./urandom -jar corona-server.jar