pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Compilando el proyecto...'
                sh 'mvn clean install'
            }
        }
        
        stage('Run Application') {
            steps {
                echo 'Ejecutando la aplicación Spring Boot...'
                sh 'mvn spring-boot:run'
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline ejecutado exitosamente!'
        }
        failure {
            echo 'El pipeline falló. Revisa los logs.'
        }
    }
}
