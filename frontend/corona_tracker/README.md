# corona_tracker

- [corona_tracker](#coronatracker)
  - [Getting Started](#getting-started)
  - [Generating corona-api](#generating-corona-api)
    - [Via Maven](#via-maven)
    - [Manual](#manual)
  - [Updating icons](#updating-icons)
A new Flutter application.

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://flutter.dev/docs/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://flutter.dev/docs/cookbook)

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.


## Generating corona-api

### Via Maven

Just run `./mvn clean install to update client`

### Manual
1. Navigate to flutter project root (`/frontend/corona_tracker/`)

2. Make sure you have a config file like `flutter.config.json` in your project root

   Example content of `flutter.config.json`:
   ```
   {
	   "browserClient": false
   }
   ```

3. [Convert](https://www.convertjson.com/yaml-to-json.htm) `/backend/corona-components/corona-api/src/main/openapi/openapi.yaml` to JSON and save it anywhere as `swagger.json`

4. Use [openapi-generator-cli](https://github.com/OpenAPITools/openapi-generator) (see ‘DownloadJAR’ section) to run the following command.
Switch out the path to the jar, input swagger.json and output folder to suit your environment and needs.
   ```
   java -jar /path/to/openapi-generator-cli.jar generate -i /path/to/swagger.json -g dart -c flutter.config.json -o corona-api
   ```

5. Update your main project’s `pubspec.yaml` to include the library
   ```yaml
   openapi:
       path: ./corona-api
   ```

6. Access the API using the import:
   ```dart2
   import 'package:openapi/api.dart';
   ```
7. Change the API base path in `/frontend/corona_tracker/corona-api/lib/api_client.dart` to `https://api.coronatrackerapp.org/api/v1`

Source of these instructions: https://github.com/juliusspencer/Flutter-OpenAPI-Pattern

## Updating icons

Update icon at `assets/icon/icon.png` and run

```
flutter pub run flutter_launcher_icons:main
```
