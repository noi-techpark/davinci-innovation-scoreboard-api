pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        TESTSERVER_TOMCAT_ENDPOINT = "http://api.innovation.tomcat02.testingmachine.eu:8080/manager/text"
        TESTSERVER_TOMCAT_CREDENTIALS = credentials('testserver-tomcat8-credentials')

        POSTGRES_URL = "jdbc:postgresql://test-pg-bdp.co90ybcr8iim.eu-west-1.rds.amazonaws.com:5432/innovation_scoreboard"
        POSTGRES_USERNAME = credentials('innovation-scoreboard-api-test-postgres-username')
        POSTGRES_PASSWORD = credentials('innovation-scoreboard-api-test-postgres-password')

        ELASTICSEARCH_SCHEME = "https"
        ELASTICSEARCH_HOST = "a2d53654bfcc40d5aa46c18627520e1d.eu-west-1.aws.found.io"
        ELASTICSEARCH_PORT = "9243"
        ELASTICSEARCH_USERNAME = credentials('innovation-scoreboard-api-test-elasticsearch-username')
        ELASTICSEARCH_PASSWORD = credentials('innovation-scoreboard-api-test-elasticsearch-password')
        ELASTICSEARCH_NAMESPACE_PREFIX = "innovation-scoreboard-test"

        S3_REGION = "eu-west-1"
        S3_BUCKET_NAME = "test-innovation-scoreboard-api"
        S3_ACCESS_KEY = credentials('innovation-scoreboard-api-test-s3-access-key')
        S3_SECRET_KEY = credentials('innovation-scoreboard-api-test-s3-secret-key')

        SECURITY_ALLOWED_ORIGINS = "https://innovation.davinci.testingmachine.eu"
        KEYCLOAK_URL = "https://auth.opendatahub.testingmachine.eu/auth"
        KEYCLOAK_REALM = "NOI"
        KEYCLOAK_CLIENT_ID = "davinci-innovation-scoreboard-api"
        KEYCLOAK_CLIENT_SECRET = credentials('innovation-scoreboard-api-test-keycloak-client-secret')
    }

    stages {
        stage('Configure') {
            steps {
                sh 'sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml'
                sh 'echo "    <servers>" >> ~/.m2/settings.xml'
                sh 'echo "        ${TESTSERVER_TOMCAT_CREDENTIALS}" >> ~/.m2/settings.xml'
                sh 'echo "    </servers>" >> ~/.m2/settings.xml'
                sh 'echo "</settings>" >> ~/.m2/settings.xml'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B -U clean test'
            }
        }
        stage('Deploy') {
            steps{
                sh 'mvn -B -U -X tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT} -Dmaven.tomcat.server=testServer -Dmaven.tomcat.path=/'
            }
        }
    }
}
