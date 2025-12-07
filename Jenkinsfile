pipeline {
    agent any

    tools {
        maven 'M2_HOME'  // Nom exact de l'installation Maven dans Jenkins
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
