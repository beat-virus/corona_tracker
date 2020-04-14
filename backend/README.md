# Corona Tracking Backend

### Setup

To develop the Service locally, start the Docker stack:

```
cd backend && docker-compose up -d
```
And start backend
```
(mvn clean install && mvn spring-boot:run)
```

To run local with prod config, e.g. for testing API AUTH:
```
( mvn clean install && \
    DB_SERVER=localhost \
    DB_NAME=corona DB_USER=corona \
    DB_PASSWORD=password \
    spring_profiles_active=prod \
    mvn spring-boot:run)
```

When seeing this in the console:
```
 d.g.w.config.SecurityConfig              : Not configured API AUTH!!
```

Run the following SQL:
```
INSERT into app_config values ('ct.apikey','api_user');
INSERT into app_config values ('ct.apiSecret','password');
```

If `spring-boot` doesn't work (`No plugin found for prefix 'spring-boot'`), try the following in `backend`:

```
mvn clean install
java -jar target/wvsvcoronatracker-0.0.1-SNAPSHOT-server.jar
```

To connect to prod db use:
```
DB_URL=corona-tracker-dev.postgres.database.azure.com \
DB_NAME=postgres \
DB_USER=... \
DB_PASSWORD=... \
spring_profiles_active=prod mvn spring-boot:run
```
