
FROM openjdk:8-jdk

LABEL maintainer=Thomas\ Eberle
LABEL last_updated=21/03/2020

WORKDIR /coronatracker

COPY corona-server/target/ /coronatracker

EXPOSE 8080

ARG VERSION

RUN ls && mv wvsvcoronatracker-${VERSION}-server.jar /tmp/corona-server.jar && \
    rm -R ./* && \
    mv /tmp/corona-server.jar ./ && \
    chmod 755 corona-server.jar

CMD spring_profiles_active=prod java -Djava.security.egd=file:/dev/./urandom -jar corona-server.jar