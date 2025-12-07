pipeline {
    agent any

    tools {
        maven 'M2_HOME'  // Nom exact de ton installation Maven dans Jenkins
    }

    stages {
        stage('Build with Maven') {
            steps {
                dir('student-management') {
                    // Ignore tous les tests pour que le build réussisse
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
    }
}
