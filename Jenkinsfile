pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Ayoubbelgacem/devops.git'
            }
        }

        stage('Build with Maven') {
            steps {
                dir('student-management') {
                    sh 'mvn clean install'
                }
            }
        }
    }
}
