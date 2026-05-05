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
                bat 'mvn clean install'
            }
        }
        
        stage('Verificar Artefacto') {
            steps {
                echo 'Verificando que el JAR se generó correctamente...'
                bat 'dir target\\*.jar'
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
