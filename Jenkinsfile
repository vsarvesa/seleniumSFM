pipeline {
    agent any

    tools {
        // Assuming you have Maven configured in Jenkins Global Tool Configuration as 'M3'
        maven 'M3'
        // Assuming JDK 21 is configured as 'JDK21'
        jdk 'JDK21'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Compiles the code and downloads dependencies
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                // Runs the TestNG suite
                // catchError allows the pipeline to continue to the Reporting stage even if tests fail
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    bat 'mvn test'
                }
            }
        }
    }

    post {
        always {
            // Generate Allure Report
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            
            // Archive screenshots from failed tests
            archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true
        }
    }
}
