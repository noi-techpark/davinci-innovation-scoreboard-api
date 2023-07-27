FROM maven:3-jdk-8-alpine as base

RUN mkdir -p /code
WORKDIR /code

# Dev
FROM base as dev

# Build
FROM base as build

COPY src /code/src
COPY pom.xml /code/pom.xml
RUN mvn -B -DskipTests install

CMD [ "mvn", "-B", "spring-boot:run" ]
