FROM maven:3-jdk-8-alpine as base

RUN mkdir -p /code

# Dev
FROM base as dev

# Build
FROM base as build

WORKDIR /code

COPY src /code/src
COPY pom.xml /code/pom.xml
RUN mvn -DskipTests install

CMD [ "mvn", "spring-boot:run" ]
