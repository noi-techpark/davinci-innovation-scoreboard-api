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

        POSTGRES_URL = "jdbc:test-pg-bdp.co90ybcr8iim.eu-west-1.rds.amazonaws.com://postgres:5432/innovation_scoreboard"
        POSTGRES_USERNAME = credentials('innovation-scoreboard-api-test-postgres-username')
        POSTGRES_PASSWORD = credentials('innovation-scoreboard-api-test-postgres-password')

        ELASTICSEARCH_HOST = "a2d53654bfcc40d5aa46c18627520e1d.eu-west-1.aws.found.io"
        ELASTICSEARCH_PORT = "9243"
        ELASTICSEARCH_USERNAME = credentials('innovation-scoreboard-api-test-elasticsearch-username')
        ELASTICSEARCH_PASSWORD = credentials('innovation-scoreboard-api-test-elasticsearch-password')
        ELASTICSEARCH_NAMESPACE_PREFIX = "innovation-scoreborard-test"

        S3_BUCKET_NAME = "test-innovation-api"
        S3_ACCESS_KEY = credentials('innovation-scoreboard-api-test-s3-access-key')
        S3_SECRET_KEY = credentials('innovation-scoreboard-api-test-s3-secret-key')

        SECURITY_JWT_SECRET = credentials('innovation-scoreboard-api-test-jwt-secret')
        SECURITY_CORS = "https://innovation.davinci.testingmachine.eu"
    }

    stages {
        stage('Configure') {
            steps {
                sh 'sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml'
                sh 'echo "    <servers>" >> ~/.m2/settings.xml'
                sh 'echo "        ${TESTSERVER_TOMCAT_CREDENTIALS}" >> ~/.m2/settings.xml'
                sh 'echo "    </servers>" >> ~/.m2/settings.xml'
                sh 'echo "</settings>" >> ~/.m2/settings.xml'

                sh 'cp src/main/resources/application.properties.example src/main/resources/application.properties'
                
                sh 'sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" src/main/resources/application.properties'
                
                sh 'sed -i -e "s%\\(flyway.user\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(flyway.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" src/main/resources/application.properties'
                
                sh 'sed -i -e "s%\\(elasticsearch.host\\s*=\\).*\\$%\\1${ELASTICSEARCH_HOST}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(elasticsearch.port\\s*=\\).*\\$%\\1${ELASTICSEARCH_PORT}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(elasticsearch.username\\s*=\\).*\\$%\\1${ELASTICSEARCH_USERNAME}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(elasticsearch.password\\s*=\\).*\\$%\\1${ELASTICSEARCH_PASSWORD}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(elasticsearch.namespace.prefix\\s*=\\).*\\$%\\1${ELASTICSEARCH_NAMESPACE_PREFIX}%" src/main/resources/application.properties'
                
                sh 'sed -i -e "s%\\(aws.credentials.accessKey\\s*=\\).*\\$%\\1${S3_ACCESS_KEY}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(aws.credentials.secretKey\\s*=\\).*\\$%\\1${S3_SECRET_KEY}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(aws.bucket.fileImport\\s*=\\).*\\$%\\1${S3_BUCKET_NAME}%" src/main/resources/application.properties'
                
                sh 'sed -i -e "s%\\(security.jwt.secret\\s*=\\).*\\$%\\1${SECURITY_JWT_SECRET}%" src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(security.cors.allowedOrigins\\s*=\\).*\\$%\\1${SECURITY_CORS}%" src/main/resources/application.properties'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B -U clean test'
            }
        }
        stage('Deploy') {
            steps{
                sh 'mvn -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT} -Dmaven.tomcat.server=testServer -Dmaven.tomcat.path=/'
            }
        }
    }
}
