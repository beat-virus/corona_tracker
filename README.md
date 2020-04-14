# Corona Tracker App

![Corona Tracker Logo](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/CoronaTrace_Logo_V3.png)

During the wirvsvirushackathon.org challenge, we collaborate on Discord to create a COVID-19 tracking app.

Planned features include:

- Self-report health status (diagnosed/cured)
- Upload your friends (anonymized, of course!) 
- Get notified immediately if one of your 1st/2nd/3rd grade friends gets diagnosed or cured
- View infected areas on a map (anonymized)
- store data of fever measurement at least twice a day
- check movement historys frequently to detect who had contact to (newly uncovered) infected persons

https://docs.google.com/document/d/18JaJj8r_-Dlp2q7oq3zp3X0w0M_lbIOY3HgEmqJCF-s/edit

![Screenshot 1](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/screenshot-10-small.png)
![Screenshot 2](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/screenshot-9-small.png)
![Screenshot 3](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/screenshot-6-small.png)
![Screenshot 5](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/screenshot-7-small.png)
![Screenshot 6](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/screenshot-8-small.png)
![Screenshot 4](https://raw.githubusercontent.com/beat-virus/corona_tracker/master/media/screenshot-3-small.png)

## Frontend
### Setup

Choose the platform for further instructions:

- Installation -> https://flutter.dev/docs/get-started/install

## Backend
### Basic instructions
- Codestyle -> https://github.com/google/styleguide
### System Requirements
#### Software for development
- Git
- OpenJDK Version 8, current update version -> https://openjdk.java.net/install/
- Maven (3.6.3)
- Lombok Plugins for the IDEs
- Docker for creating the docker image
- pgAdmin to administrate all postgreSQL-databases ("Klicki-Bunti")
- PostgreSQL for local develoment (11.x?)

### Preparations for development
1. In the root folder, please execute the following command: `mvn clean install` -> **IMPORTANT**: You need to set the correct JDK Version either in your "JAVA_HOME" environment variable or in your IDE, otherwise maven will throw errors.
2. You might need to refresh the root folder.

### Setup

See [backend/README.md](https://github.com/beat-virus/corona_tracker/blob/master/backend/README.md)

## Contributing

Recommended GitHub workflow:

### Repository setup

Open https://github.com/beat-virus/corona_tracker in your browser.

Click the "Fork" button to clone the repository to your own GitHub account.

```bash
# Clone your fork to localhost (replace USERNAME with your GitHub username):
git clone git@github.com:USERNAME/corona_tracker.git

# Change to newly created directory
cd corona_tracker

# Add the original repository as "upstream"
git remote add upstream git@github.com:beat-virus/corona_tracker.git
git remote set-url --push upstream no_push
```

### Work on a feature

Get the newest code from the original repository:

```bash
cd corona_tracker
git fetch upstream
git checkout master
git rebase upstream/master
```

Create a new branch for your feature:

```bash
git checkout -b myfeature
```

When you're done, commit changes and push branch:

```
git add <files>
git commit
git push origin myfeature
```

Create a pull request (PR):

Open https://github.com/USERNAME/corona_tracker in your browser (replace USERNAME with your GitHub username).

Click the "Compare & Pull Request" button.

## Links

- Devpost: https://devpost.com/software/1_008_a_corona_tracking_coronatracking
