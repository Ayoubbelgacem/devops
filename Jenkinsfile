pipeline {
    agent any

    tools {
        maven 'Maven 3.6.3' // Nom de l'installation Maven dans Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Ayoubbelgacem/devops.git'
            }
        }

        stage('Build with Maven') {
            steps {
                dir('student-management') {  // va dans le sous-dossier
                    sh 'mvn clean install'
                }
            }
        }
    }
}
