pipeline {
    agent any

    tools {
        maven 'Maven 3.6.3' // Nom de l'installation Maven dans Jenkins
    }

    stages {
        stage('Build with Maven') {
            steps {
                dir('student-management') {
                    sh 'mvn clean install'
                }
            }
        }
    }
}
