// Jenkinsfile for pipeline demonstration
pipeline {
    agent any
    environment {
        JAVA_HOME = tool name: 'jdk11', type: 'jdk'
        GRADLE_HOME = tool name: 'Gradle_7', type: 'gradle'
        PATH = "${env.GRADLE_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Install Node.js') {
            steps {
                // Assumes Node.js is available as a tool in Jenkins
                script {
                    def nodeHome = tool name: 'NodeJS_18', type: 'nodejs'
                    env.PATH = "${nodeHome}/bin:${env.PATH}"
                }
            }
        }
        stage('Install NPM dependencies') {
            steps {
                sh 'npm install'
            }
        }
        stage('Install Gradle dependencies') {
            steps {
                sh 'gradle --no-daemon build -x test'
            }
        }
        stage('Run Groovy Tests') {
            steps {
                sh 'npm run test:groovy'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
        failure {
            echo 'Tests failed. Failing the pipeline.'
        }
    }
}
