pipeline {
    agent {
        dockerfile {
            dir 'backend'
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        POSTGRES_URL = ""
        POSTGRES_USERNAME = credentials('innovation-scoreboard-prod-postgres-username')
        POSTGRES_PASSWORD = credentials('innovation-scoreboard-prod-postgres-password')

        ELASTICSEARCH_HOST = ""
        ELASTICSEARCH_PORT = ""
        ELASTICSEARCH_USERNAME = ""
        ELASTICSEARCH_PASSWORD = ""

        S3_BUCKET_NAME = ""
        S3_ACCESS_KEY = ""
        S3_SECRET_KEY = ""

        SECURITY_JWT_SECRET = ""
        SECURITY_CORS = "https://innovation-scoreboard.davinci.bz.it"
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
        stage('Build') {
            steps {
                sh 'mvn -B -U clean package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/innovation-scoreboard.war', onlyIfSuccessful: true
            }
        }
    }
}
