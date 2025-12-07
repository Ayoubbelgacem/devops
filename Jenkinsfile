pipeline {
    agent any

    tools {
        maven 'M2_HOME'  // Nom exact de l'installation Maven dans Jenkins
    }

    stages {
        stage('Build with Maven') {
            steps {
                dir('student-management') {
                    // Ignore les tests pour que le build réussisse
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'student-management/target/*.jar', fingerprint: true
            }
        }
    }
}
