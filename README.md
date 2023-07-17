<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: CC0-1.0
-->

# Innovation Scoreboard

The innovation scoreboard is a project that aims to visualize the innovation data from South Tyrol and other Italien regions in a user friendly way.

This repository contains the source code for the innovation scoreboard backend.

[![REUSE Compliance](https://github.com/noi-techpark/davinci-innovation-scoreboard-api/actions/workflows/reuse.yml/badge.svg)](https://github.com/noi-techpark/odh-docs/wiki/REUSE#badges)
[![CI](https://github.com/noi-techpark/davinci-innovation-scoreboard-api/actions/workflows/main.yml/badge.svg)](https://github.com/noi-techpark/davinci-innovation-scoreboard-api/actions/workflows/main.yml)

## Table of contents

- [Gettings started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Source code](#source-code)
  - [Setup authentication server](#setup-authentication-server)
  - [Execute without Docker](#execute-without-docker)
  - [Execute with Docker](#execute-with-docker)
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

### Setup authentication server

#### How to setup NOI authentication server locally?

- [Here](https://github.com/noi-techpark/authentication-server) you can find how to run the server locally
- Create a new realm following these [steps](https://github.com/noi-techpark/authentication-server/blob/master/docs/noi-authentication-server.md#realm)

##### How to register this application in your local authentication server?

1. Open the previously created realm
2. Create a new client (Clients -> Create)

| Property | Value           |
| -------- | --------------- |
| ClientID | davinci-innovation-scoreboard-api |

3. Client Settings

| Property | Value               |
| -------- | ------------------- |
| Access Type | bearer-only |

4. Navigate to Roles

Add following roles: project_manager

##### How to create a user or assign a user the necessary roles for this application?

1. Go to users
2. Create user or select user (View users)
3. Assign roles: Role Mappings -> Client Roles -> davinci-innovation-scoreboard-api

#### How to create a client to generate tokens for testing purposes?

1. Open the previously created realm
2. Create a new client (Clients -> Create)

| Property | Value               |
| -------- | ------------------- |
| ClientID | davinci-innovation-scoreboard-api-client |

3. Client Settings

| Property                     | Value  |
| ---------------------------- | ------ |
| Access Type                  | public |
| Standard Flow Enabled        | Off    |
| Implicit Flow Enabled        | Off    |
| Direct Access Grants Enabled | On     |

4. Navigate to Scope

| Property                                          | Value                                |
| ------------------------------------------------- | ------------------------------------ |
| Full Scope Allowed                                | Off                                  |
| Client Roles -> odh-mobility-v2 -> Assigned Roles | Move available roles to assigned roles |

5. Generate a new token

```sh
curl --location --request POST 'http://localhost:8080/auth/realms/NOI/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username={USERNAME}' \
--data-urlencode 'password={PASSWORD}' \
--data-urlencode 'client_id=davinci-innovation-scoreboard-api-client'
```

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

### REUSE

This project is [REUSE](https://reuse.software) compliant, more information about the usage of REUSE in NOI Techpark repositories can be found [here](https://github.com/noi-techpark/odh-docs/wiki/Guidelines-for-developers-and-licenses#guidelines-for-contributors-and-new-developers).

Since the CI for this project checks for REUSE compliance you might find it useful to use a pre-commit hook checking for REUSE compliance locally. The [pre-commit-config](.pre-commit-config.yaml) file in the repository root is already configured to check for REUSE compliance with help of the [pre-commit](https://pre-commit.com) tool.

Install the tool by running:
```bash
pip install pre-commit
```
Then install the pre-commit hook via the config file by running:
```bash
pre-commit install
```
