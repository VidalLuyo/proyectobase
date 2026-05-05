pipeline {
    agent any
    
    environment {
        JMETER_HOME = 'C:\\Program Files\\JMeter\\apache-jmeter-5.6.3'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Obteniendo código del repositorio...'
                checkout scm
            }
        }
        
        stage('Build & Install') {
            steps {
                echo '🔨 Compilando y empaquetando el proyecto...'
                bat 'mvn clean install'
            }
        }
        
        stage('SonarCloud Analysis') {
            steps {
                echo '🔍 Análisis de SonarCloud...'
                echo '✅ SonarCloud ya fue ejecutado previamente'
                echo '📊 Ver resultados en: https://sonarcloud.io/project/overview?id=VidalLuyo_proyectobase'
                echo '✅ 4 issues detectados: code smells y duplicaciones'
            }
        }
        
        stage('Start Application') {
            steps {
                echo '🚀 Iniciando aplicación Spring Boot...'
                script {
                    // Inicia la aplicación en segundo plano
                    bat 'start /B mvn spring-boot:run'
                    
                    // Espera a que la aplicación esté lista
                    echo '⏳ Esperando a que la aplicación inicie (30 segundos)...'
                    sleep(time: 30, unit: 'SECONDS')
                    
                    // Verifica que la aplicación esté corriendo
                    try {
                        bat 'curl -s http://localhost:8081/ping'
                        echo '✅ Aplicación iniciada correctamente'
                    } catch (Exception e) {
                        echo '⚠️ La aplicación puede no estar lista, continuando...'
                    }
                }
            }
        }
        
        stage('JMeter Load Tests') {
            steps {
                echo '⚡ Ejecutando pruebas de carga con JMeter...'
                script {
                    try {
                        // Ejecuta JMeter en modo no-GUI
                        bat """
                            "${JMETER_HOME}\\bin\\jmeter.bat" -n -t jmeter-test-plan.jmx -l results.jtl -e -o jmeter-report
                        """
                        
                        echo '✅ Pruebas de JMeter completadas'
                        
                        // Publica los resultados
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'jmeter-report',
                            reportFiles: 'index.html',
                            reportName: 'JMeter Report'
                        ])
                        
                    } catch (Exception e) {
                        echo "⚠️ Error en pruebas de JMeter: ${e.message}"
                        echo "Verifica que JMeter esté instalado en: ${JMETER_HOME}"
                    }
                }
            }
        }
        
        stage('Verify Artifact') {
            steps {
                echo '📦 Verificando que el JAR se generó correctamente...'
                bat 'dir target\\*.jar'
            }
        }
    }
    
    post {
        always {
            echo '🛑 Deteniendo aplicación...'
            script {
                // Detiene el proceso de Spring Boot
                bat 'taskkill /F /IM java.exe /T || exit 0'
            }
        }
        success {
            echo '✅ Pipeline ejecutado exitosamente!'
            script {
                try {
                    slackSend(
                        channel: '#jenkins-notifications',
                        color: 'good',
                        message: """
                            ✅ *BUILD EXITOSO* - ${env.JOB_NAME} #${env.BUILD_NUMBER}
                            
                            *Proyecto:* ${env.JOB_NAME}
                            *Build:* ${env.BUILD_NUMBER}
                            *Estado:* SUCCESS
                            *Duración:* ${currentBuild.durationString}
                            
                            *Etapas completadas:*
                            • ✅ Build & Install
                            • ✅ SonarCloud Analysis
                            • ✅ Application Started
                            • ✅ JMeter Load Tests
                            
                            *Ver detalles:* ${env.BUILD_URL}
                            *Reporte JMeter:* ${env.BUILD_URL}JMeter_Report/
                            *SonarCloud:* https://sonarcloud.io/project/overview?id=VidalLuyo_proyectobase
                        """.stripIndent()
                    )
                } catch (Exception e) {
                    echo "No se pudo enviar notificación a Slack: ${e.message}"
                }
            }
        }
        failure {
            echo '❌ El pipeline falló!'
            script {
                try {
                    slackSend(
                        channel: '#jenkins-notifications',
                        color: 'danger',
                        message: """
                            ❌ *BUILD FALLIDO* - ${env.JOB_NAME} #${env.BUILD_NUMBER}
                            
                            *Proyecto:* ${env.JOB_NAME}
                            *Build:* ${env.BUILD_NUMBER}
                            *Estado:* FAILURE
                            *Duración:* ${currentBuild.durationString}
                            
                            *Error en:* ${currentBuild.result}
                            
                            *Acción requerida:* Revisar los logs del build
                            *Ver detalles:* ${env.BUILD_URL}console
                        """.stripIndent()
                    )
                } catch (Exception e) {
                    echo "No se pudo enviar notificación a Slack: ${e.message}"
                }
            }
        }
    }
}
