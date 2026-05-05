pipeline {
    agent any
    
    tools {
        maven 'Maven 3.6.3'
        jdk 'JDK 11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Compilando el proyecto...'
                bat 'mvn clean install'
            }
        }
        
        stage('Run Application') {
            steps {
                echo 'Ejecutando la aplicación Spring Boot...'
                bat 'mvn spring-boot:run'
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
