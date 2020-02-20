# Innovation Scoreboard

The innovation scoreboard is a project that aims to visualize the innovation data from South Tyrol and other Italien regions in a user friendly way.

This repository contains the source code for the innovation scoreboard backend.

## Table of contents

- [Getting started](#getting-started)
- [Running tests](#running-tests)
- [Deployment](#deployment)
- [User management](#user-management)
- [Information](#information)

## Getting started

These instructions will get you a copy of the project up and running
on your local machine for development and testing purposes.

### Prerequisites

To build the project, the following prerequisites must be met:

- Java JDK 1.8 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
- [Maven](https://maven.apache.org/) 3.x
- Postgres Database 11
- Elasticsearch 7.2
- AWS account with S3 access
- Kibana (optional)
- [Authentication server](https://github.com/noi-techpark/authentication-server)

If you want to run the application using [Docker](https://www.docker.com/), the environment is already set up with all dependencies for you. You only have to install [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) and follow the instruction in the [dedicated section](#execute-with-docker).

However, the [authentication server](https://github.com/noi-techpark/authentication-server) is not part of the Docker environment and needs to be started separately.

### Source code

Get a copy of the repository:

```bash
git clone https://github.com/noi-techpark/davinci-innovation-scoreboard-api.git
```

Change directory:

```bash
cd davinci-innovation-scoreboard-api/
```

### Execute without Docker

Copy the file `src/main/resources/application.properties` to `src/main/resources/application-local.properties` and adjust the variables that get their values from environment variables. You can take a look at the `.env.example` for some help.

Build the project:

```bash
mvn -Dspring.profiles.active=local clean install
```

Run the project:

```bash
mvn -Dspring.profiles.active=local spring-boot:run
```

The service will be available at localhost and your specified server port.

To execute the test you can run the following command:

```bash
mvn clean test
```

### Execute with Docker

Copy the file `.env.example` to `.env` and adjust the configuration parameters.

Then you can start the application using the following command:

```bash
docker-compose up
```

The service will be available at localhost and your specified server port.

To execute the test you can run the following command:

```bash
docker-compose run --rm app mvn clean test
```

### Execute with IntelliJ or another IDE

If you want to run the application from an IDE and don't use the Docker container for it, then you still have the possibility to start all dependencies using Docker.

1. Copy the file `src/main/resources/application.properties` to `src/main/resources/application-local.properties` and adjust the settings if needed.

2. Startup external dependencies

```bash
docker-compose -f docker-compose.dependencies.yml up
```

3. Run application in your prefered IDE as a maven project

```
mvn:spring-boot run -Dspring-boot.run.profiles=local
```

## User management

User management is handled by the NOI Authentication server. This application offers 1 role, that is used to protect CSV upload endpoints.

- project_manager

## Information

### Support

For support, please contact [info@davinci.bz.it](mailto:info@davinci.bz.it).

### Contributing

If you'd like to contribute, please follow the following instructions:

- Fork the repository.

- Checkout a topic branch from the `development` branch.

- Make sure the tests are passing.

- Create a pull request against the `development` branch.

### Documentation

More documentation can be found at [https://opendatahub.readthedocs.io/en/latest/index.html](https://opendatahub.readthedocs.io/en/latest/index.html).

### License

The code in this project is licensed under the GNU AFFERO GENERAL PUBLIC LICENSE Version 3 license. See the [LICENSE.md](LICENSE.md) file for more information.
