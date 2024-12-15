pipeline {
    agent any

    tools {
        maven 'maven-3-3-9'
    }

    stages {
        stage('Build') {
            steps {
                 sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'mvn test -P unitTest'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Integration Test') {
            steps {
                sh 'mvn test -P integrationTest'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}