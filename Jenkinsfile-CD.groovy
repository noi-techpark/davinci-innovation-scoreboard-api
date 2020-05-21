def getEnvironment(branch_name, tag_name) {
    if (tag_name != null) {
        return 'prod'
    }
    
    if (branch_name == 'master') {
        return 'test'
    }
    
    return 'dev'
}

def getVersion(environment, build_number, tag_name) {
    if (environment == 'prod') {
        return tag_name
    }

    return build_number
}

def getDockerTag(environment, version) {
    return environment + '-' + version
}

def getPostgresUrl(environment) {
    if (environment == 'prod') return 'jdbc:postgresql://postgres-prod.co90ybcr8iim.eu-west-1.rds.amazonaws.com:5432/innovation_scoreboard'
    if (environment == 'test') return '"jdbc:postgresql://test-pg-bdp.co90ybcr8iim.eu-west-1.rds.amazonaws.com:5432/innovation_scoreboard"'
}

def getElasticsearchUrl(environment) {
    if (environment == 'prod') return '05068ca494804a0b86352e68fadb3457.eu-west-1.aws.found.io'
    if (environment == 'test') return 'a2d53654bfcc40d5aa46c18627520e1d.eu-west-1.aws.found.io'
}

def getKeycloakUrl(environment) {
    if (environment == 'prod') return 'https://auth.opendatahub.bz.it/auth'
    if (environment == 'test') return 'https://auth.opendatahub.testingmachine.eu/auth'
}

def getSecurityAllowedOrigins(environment) {
    if (environment == 'prod') return 'https://innovation.davinci.bz.it'
    if (environment == 'test') return 'https://innovation.davinci.testingmachine.eu'
}

pipeline {
    agent any

    environment {
        ENVIRONMENT = getEnvironment(env.BRANCH_NAME, env.TAG_NAME)
        VERSION = getVersion(env.ENVIRONMENT, env.BUILD_NUMBER, env.TAG_NAME)

        DOCKER_PROJECT_NAME = "innovation-scoreboard-api"
        DOCKER_IMAGE = '755952719952.dkr.ecr.eu-west-1.amazonaws.com/innovation-scoreboard-api'
        DOCKER_TAG = getDockerTag(env.ENVIRONMENT, env.VERSION)
    }

    stages {
        stage('Configure') {
            environment {
                SERVER_PORT = "1001"
                LOG_APPLICATION_NAME = "innovation-scoreboard-api"

                POSTGRES_URL = getPostgresUrl(env.ENVIRONMENT)
                POSTGRES_USERNAME = credentials("innovation-scoreboard-api-${ENVIRONMENT}-postgres-username")
                POSTGRES_PASSWORD = credentials("innovation-scoreboard-api-${ENVIRONMENT}-postgres-password")

                ELASTICSEARCH_SCHEME = "https"
                ELASTICSEARCH_HOST = getElasticsearchUrl(env.ENVIRONMENT)
                ELASTICSEARCH_PORT = "9243"
                ELASTICSEARCH_USERNAME = credentials("innovation-scoreboard-api-${ENVIRONMENT}-elasticsearch-username")
                ELASTICSEARCH_PASSWORD = credentials("innovation-scoreboard-api-${ENVIRONMENT}-elasticsearch-password")
                ELASTICSEARCH_NAMESPACE_PREFIX = "innovation-scoreboard-${ENVIRONMENT}"

                S3_REGION = "eu-west-1"
                S3_BUCKET_NAME = "${ENVIRONMENT}-innovation-scoreboard-api"
                S3_ACCESS_KEY = credentials("innovation-scoreboard-api-${ENVIRONMENT}-s3-access-key")
                S3_SECRET_KEY = credentials("innovation-scoreboard-api-${ENVIRONMENT}-s3-secret-key")

                SECURITY_ALLOWED_ORIGINS = getSecurityAllowedOrigins(env.ENVIRONMENT)
                KEYCLOAK_URL = getKeycloakUrl(env.ENVIRONMENT)
                KEYCLOAK_REALM = "noi"
                KEYCLOAK_CLIENT_ID = "davinci-innovation-scoreboard-api"
                KEYCLOAK_CLIENT_SECRET = credentials("innovation-scoreboard-api-${ENVIRONMENT}-keycloak-client-secret")
                KEYCLOAK_SSL_REQUIRED = "none"
            }
            steps {
                sh """
                    rm -f .env
                    cp .env.example .env
                    echo 'COMPOSE_PROJECT_NAME=${DOCKER_PROJECT_NAME}' >> .env
                    echo 'DOCKER_IMAGE=${DOCKER_IMAGE}' >> .env
                    echo 'DOCKER_TAG=${DOCKER_TAG}' >> .env

					echo 'SERVER_PORT=${SERVER_PORT}' >> .env
                    echo 'LOG_APPLICATION_NAME=${LOG_APPLICATION_NAME}' >> .env

                    echo 'POSTGRES_URL=${POSTGRES_URL}' >> .env
                    echo 'POSTGRES_USERNAME=${POSTGRES_USERNAME}' >> .env
                    echo 'POSTGRES_PASSWORD=${POSTGRES_PASSWORD}' >> .env

                    echo 'ELASTICSEARCH_SCHEME=${ELASTICSEARCH_SCHEME}' >> .env
                    echo 'ELASTICSEARCH_HOST=${ELASTICSEARCH_HOST}' >> .env
                    echo 'ELASTICSEARCH_PORT=${ELASTICSEARCH_PORT}' >> .env
                    echo 'ELASTICSEARCH_USERNAME=${ELASTICSEARCH_USERNAME}' >> .env
                    echo 'ELASTICSEARCH_PASSWORD=${ELASTICSEARCH_PASSWORD}' >> .env
                    echo 'ELASTICSEARCH_NAMESPACE_PREFIX=${ELASTICSEARCH_NAMESPACE_PREFIX}' >> .env

                    echo 'S3_REGION=${S3_REGION}' >> .env
                    echo 'S3_BUCKET_NAME=${S3_BUCKET_NAME}' >> .env
                    echo 'S3_ACCESS_KEY=${S3_ACCESS_KEY}' >> .env
                    echo 'S3_SECRET_KEY=${S3_SECRET_KEY}' >> .env

                    echo 'SECURITY_ALLOWED_ORIGINS=${SECURITY_ALLOWED_ORIGINS}' >> .env
                    echo 'KEYCLOAK_URL=${KEYCLOAK_URL}' >> .env
                    echo 'KEYCLOAK_REALM=${KEYCLOAK_REALM}' >> .env
                    echo 'KEYCLOAK_CLIENT_ID=${KEYCLOAK_CLIENT_ID}' >> .env
                    echo 'KEYCLOAK_CLIENT_SECRET=${KEYCLOAK_CLIENT_SECRET}' >> .env
                    echo 'KEYCLOAK_SSL_REQUIRED=${KEYCLOAK_SSL_REQUIRED}' >> .env
                """
            }
        }

        stage('Test') {
            steps {
                sh '''
                    docker network create authentication || true
                    docker-compose --no-ansi build --pull --build-arg JENKINS_USER_ID=$(id -u jenkins) --build-arg JENKINS_GROUP_ID=$(id -g jenkins)
                    docker-compose --no-ansi run --rm --no-deps -u $(id -u jenkins):$(id -g jenkins) app mvn clean test
                '''
            }
        }
        stage('Build') {
            steps {
                sh '''
                    aws ecr get-login --region eu-west-1 --no-include-email | bash
                    docker-compose --no-ansi -f docker-compose.build.yml build --pull
                    docker-compose --no-ansi -f docker-compose.build.yml push
                '''
            }
        }
        stage('Deploy') {
            steps {
               sshagent(['jenkins-ssh-key']) {
                    sh """
                        ansible-galaxy install --force -r ansible/requirements.yml
                        ansible-playbook --limit=${ENVIRONMENT} ansible/deploy.yml --extra-vars "build_number=${BUILD_NUMBER}"
                    """
                }
            }
        }
    }
}
