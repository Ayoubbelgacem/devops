pipeline {
    agent any

    tools {
        maven 'M2_HOME'  // correspond à l'installation Maven existante dans Jenkins
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
