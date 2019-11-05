# Innovation Scoreboard

The innovation scoreboard is a project that aims to visualize the innovation data form South Tyrol and other Italien regions in a user friendly way.

This repository contains the source code for the innovation scoreboard backend.

## Table of contents

- [Getting started](#getting-started)
- [Running tests](#running-tests)
- [Deployment](#deployment)
- [Docker environment](#docker-environment)
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

For a ready to use Docker environment with all prerequisites already installed and prepared, you can check out the [Docker environment](#docker-environment) section.

### Source code

Get a copy of the repository:

```bash
git clone https://github.com/noi-techpark/davinci-innovation-scoreboard-api.git
```

Change directory:

```bash
cd davinci-innovation-scoreboard-api/
```

### Configure

Copy the file `src/main/resources/application.properties.example` to `src/main/resources/application.properties` and adjust the settings if needed.

The defaults are already configured, that the you can use the Docker environment right away without any modifications.

### Development

Build the project:

```bash
mvn clean install
```

The website will be available at [http://localhost:8080](http://localhost:8080).

## Running tests

The unit tests can be executed with the following command:

```bash
mvn clean test
```

## Deployment

To build the application you have to first adjust the configuation to your needs and then create a war file using the following command:

```bash
mvn clean package
```

The resulting war file can then be executed using a Tomcat server.

## Docker environment

For the project a Docker environment is already prepared and ready to use with all necessary prerequisites.

These Docker containers are the same as used by the continuous integration servers.

There are two docker-compose files in this project:

- **docker-compose.yml**: It will start all necessary dependencies and the spring boot application.
- **docker-compose-dependencies.yml**: It will only start the dependencies. This is useful during development to be able to run the spring boot application in your IDE.

### Installation

Install [Docker](https://docs.docker.com/install/) (with Docker Compose) locally on your machine.

### Start and stop the containers

Before start working you have to start the Docker containers:

```bash
docker-compose up --build --detach
```

After finished working you can stop the Docker containers:

```bash
docker-compose stop
```

### Running commands inside the container

When the containers are running, you can execute any command inside the environment. Just replace the dots `...` in the following example with the command you wish to execute:

```bash
docker-compose exec java /bin/sh -c "..."
```

Some examples are:

```bash
docker-compose exec java /bin/sh -c "mvn clean install"

# or

docker-compose exec java /bin/sh -c "mvn clean test"
```

### Running the application in IntelliJ or any other IDE for local development

If you want to run the application from an IDE and don't use the Docker container for it, then you still have the possibility to start all dependencies using Docker.

1. Startup external dependencies

```bash
docker-compose -f docker-compose-dependencies.yml up
```

2. Run application in your prefered IDE as a maven project

```
mvn:spring-boot run
```

## User management

**IMPORTANT!!**

Reset the password of the admin account as soon as you setup the application for the first time.
Check out the swagger documentation `/swagger-ui.html` on how to reset a users password.

In order to retrieve a valid token to use in the swagger UI make a request to the login endpoint.

```
curl -d '{"email": "info@davinci.bz.it", "password": "password"}' -H "Content-Type: application/json" -X POST http://localhost:8080/v1/authenticate
```

Copy the token and paste it in the swagger authentication popup, prefixing the token with `Bearer ${token}`.
Now you can use the UI to reset your users password and as an *ADMIN* you can also create new accounts.

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
