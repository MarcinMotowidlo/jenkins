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

    environment {
            NEXUS_VERSION = "nexus3"
            NEXUS_PROTOCOL = "http"
            NEXUS_URL = "localhost:8081"
            NEXUS_REPOSITORY = "maven-nexus-repo"
            NEXUS_CREDENTIAL_ID = "jenkins-nexus-user"
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

        stage("Publish to Nexus Repository Manager") {
                    steps {
                        script {
                            pom = readMavenPom file: "pom.xml";
                            filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                            echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                            artifactPath = filesByGlob[0].path;
                            artifactExists = fileExists artifactPath;
                            if(artifactExists) {
                                echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                                nexusArtifactUploader(
                                    nexusVersion: NEXUS_VERSION,
                                    protocol: NEXUS_PROTOCOL,
                                    nexusUrl: NEXUS_URL,
                                    groupId: pom.groupId,
                                    version: pom.version,
                                    repository: NEXUS_REPOSITORY,
                                    credentialsId: NEXUS_CREDENTIAL_ID,
                                    artifacts: [
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: "pom.xml",
                                        type: "pom"]
                                    ]
                                );
                            } else {
                                error "*** File: ${artifactPath}, could not be found";
                            }
                        }
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