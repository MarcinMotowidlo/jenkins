pipeline {
    agent any

    parameters {
        string(name: 'DOCKER_IMAGE', defaultValue: 'jenkins-demo', description: 'Docker image name')
        string(name: 'DOCKER_USERNAME', defaultValue: 'xxx', description: 'Tag of docker image')
        string(name: 'DOCKER_TAG', defaultValue: 'latest', description: 'Tag of docker image')
    }

    tools {
        maven 'maven-3-3-9'
    }

    stages {
        stage('Initialize'){
            def dockerHome = tool 'myDocker'
            env.PATH = "${dockerHome}/bin:${env.PATH}"
        }

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

        stage('Maven Install') {
            agent {
                docker {
                    image 'maven:3.3.9'
                }
            }
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Docker Build') {
            agent any
            steps {
                sh "docker build -t ${params.DOCKER_USERNAME}/${params.DOCKER_IMAGE}:${params.DOCKER_TAG} ."
                }
        }


        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}