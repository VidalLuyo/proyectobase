# 📄 DOCUMENTO DE ENTREGA - PIPELINE DE INTEGRACIÓN CONTINUA

**Estudiante:** Jesús Vidal Luyo  
**Proyecto:** proyectobase  
**Fecha:** Mayo 2026  
**Repositorio:** https://github.com/VidalLuyo/proyectobase

---

## 1. DESCRIPCIÓN DEL PROCESO REALIZADO

### Objetivo
Implementar un pipeline de integración continua completo que automatice el proceso de build, análisis de código, pruebas de carga y notificaciones.

### Herramientas Integradas

#### 🔧 Jenkins
- **Versión:** 2.541.3
- **Función:** Orquestador del pipeline CI/CD
- **Configuración:**
  - Proyecto tipo Pipeline
  - SCM: Git (GitHub)
  - Trigger: Manual (Build Now)
  - Jenkinsfile en la raíz del proyecto

#### 🔍 SonarCloud
- **URL:** https://sonarcloud.io
- **Organization:** vidalluyo
- **Project Key:** VidalLuyo_proyectobase
- **Función:** Análisis estático de código
- **Configuración:**
  - Token de autenticación configurado en Jenkins
  - Integración mediante plugin SonarQube Scanner
  - Análisis automático en cada build

#### ⚡ Apache JMeter
- **Versión:** 5.6.3
- **Ruta:** C:\Program Files\JMeter\apache-jmeter-5.6.3
- **Función:** Pruebas de carga y rendimiento
- **Configuración:**
  - Plan de pruebas: jmeter-test-plan.jmx
  - 50 usuarios concurrentes
  - 10 iteraciones por usuario
  - Endpoints probados: GET /products, POST /login

#### 💬 Slack
- **Workspace:** [Tu workspace]
- **Canal:** #jenkins-notifications
- **Función:** Notificaciones automáticas
- **Configuración:**
  - Plugin Slack Notification instalado
  - Token configurado en Jenkins
  - Notificaciones de éxito y error

---

## 2. EXPLICACIÓN DE LA INTEGRACIÓN DE HERRAMIENTAS

### Flujo de Integración

```
GitHub → Jenkins → SonarCloud
                 ↓
              Maven Build
                 ↓
           Spring Boot App
                 ↓
              JMeter Tests
                 ↓
         Slack Notification
```

### Conexión entre Herramientas

#### Jenkins ↔ GitHub
- **Método:** Git SCM
- **Configuración:** URL del repositorio en Jenkinsfile
- **Trigger:** Checkout automático al iniciar build
- **Código:**
```groovy
stage('Checkout') {
    steps {
        checkout scm
    }
}
```

#### Jenkins ↔ SonarCloud
- **Método:** Plugin SonarQube Scanner + Token
- **Configuración:**
  - Token almacenado en Jenkins Credentials
  - Servidor SonarCloud configurado en Jenkins System
- **Código:**
```groovy
withSonarQubeEnv('SonarCloud') {
    bat '''
        mvn sonar:sonar ^
        -Dsonar.projectKey=VidalLuyo_proyectobase ^
        -Dsonar.organization=vidalluyo ^
        -Dsonar.host.url=https://sonarcloud.io
    '''
}
```

#### Jenkins ↔ JMeter
- **Método:** Ejecución directa mediante bat command
- **Configuración:**
  - Variable de entorno JMETER_HOME
  - Plan de pruebas en el repositorio
- **Código:**
```groovy
bat """
    "${JMETER_HOME}\\bin\\jmeter.bat" -n -t jmeter-test-plan.jmx -l results.jtl -e -o jmeter-report
"""
```

#### Jenkins ↔ Slack
- **Método:** Plugin Slack Notification
- **Configuración:**
  - Token de integración en Jenkins
  - Canal configurado en System settings
- **Código:**
```groovy
slackSend(
    channel: '#jenkins-notifications',
    color: 'good',
    message: "✅ BUILD EXITOSO - ${env.JOB_NAME} #${env.BUILD_NUMBER}"
)
```

---

## 3. ETAPAS DEL PIPELINE

### Stage 1: Checkout (📥)
- **Duración:** ~5 segundos
- **Acción:** Clona el repositorio desde GitHub
- **Resultado:** Código fuente disponible en workspace

### Stage 2: Build & Install (🔨)
- **Duración:** ~30-40 segundos
- **Comando:** `mvn clean install`
- **Acciones:**
  - Compila el código Java
  - Ejecuta tests unitarios
  - Genera el archivo JAR
- **Artefacto:** target/demo-1.0.jar

### Stage 3: SonarCloud Analysis (🔍)
- **Duración:** ~20-30 segundos
- **Acción:** Envía código a SonarCloud para análisis
- **Métricas analizadas:**
  - Code Smells
  - Bugs
  - Vulnerabilidades
  - Duplicaciones
  - Cobertura de código

### Stage 4: Start Application (🚀)
- **Duración:** ~30 segundos
- **Comando:** `mvn spring-boot:run`
- **Acción:** Inicia la aplicación en puerto 8081
- **Verificación:** Ping a /ping endpoint

### Stage 5: JMeter Load Tests (⚡)
- **Duración:** ~1-2 minutos
- **Configuración:**
  - 50 usuarios concurrentes
  - Ramp-up: 10 segundos
  - 10 iteraciones por usuario
  - Total: 1000 peticiones
- **Endpoints probados:**
  - GET http://localhost:8081/products
  - POST http://localhost:8081/login

### Stage 6: Verify Artifact (📦)
- **Duración:** ~2 segundos
- **Acción:** Verifica que el JAR se generó correctamente
- **Comando:** `dir target\*.jar`

### Post: Always (🛑)
- **Acción:** Detiene la aplicación Spring Boot
- **Comando:** `taskkill /F /IM java.exe`

### Post: Success/Failure (💬)
- **Acción:** Envía notificación a Slack
- **Contenido:** Estado del build, duración, enlaces

---

## 4. RESULTADOS OBTENIDOS

### ✅ Ejecución del Pipeline

**Build #[NÚMERO]**
- **Estado:** SUCCESS / FAILURE
- **Duración total:** [X] minutos [Y] segundos
- **Fecha:** [FECHA]

**Etapas completadas:**
- ✅ Checkout: OK
- ✅ Build & Install: OK
- ✅ SonarCloud Analysis: OK
- ✅ Start Application: OK
- ✅ JMeter Load Tests: OK
- ✅ Verify Artifact: OK

### 🔍 Análisis de SonarCloud

**Métricas de Calidad:**
- **Bugs:** [NÚMERO]
- **Vulnerabilidades:** [NÚMERO]
- **Code Smells:** [NÚMERO]
- **Cobertura:** [PORCENTAJE]%
- **Duplicaciones:** [PORCENTAJE]%
- **Líneas de código:** [NÚMERO]

**Problemas Detectados:**

1. **Variable no utilizada**
   - **Archivo:** ApiController.java
   - **Línea:** 20
   - **Descripción:** Variable `unused` declarada pero no utilizada
   - **Severidad:** Minor
   - **Mejora propuesta:** Eliminar la variable o utilizarla

2. **Complejidad ciclomática alta**
   - **Archivo:** ApiController.java
   - **Método:** check()
   - **Descripción:** Anidamiento excesivo de if statements
   - **Severidad:** Major
   - **Mejora propuesta:** Simplificar la lógica o extraer a métodos separados

3. **[OTRO PROBLEMA DETECTADO]**
   - **Descripción:** [DETALLES]
   - **Mejora propuesta:** [SOLUCIÓN]

### ⚡ Resultados de JMeter

**Configuración de la prueba:**
- Usuarios concurrentes: 50
- Duración: ~2 minutos
- Total de peticiones: 1000

**Métricas de Rendimiento:**

| Endpoint | Peticiones | Tiempo Promedio | Min | Max | Throughput | Error % |
|----------|-----------|-----------------|-----|-----|------------|---------|
| GET /products | 500 | [X]ms | [X]ms | [X]ms | [X] req/s | [X]% |
| POST /login | 500 | [X]ms | [X]ms | [X]ms | [X] req/s | [X]% |
| **TOTAL** | **1000** | **[X]ms** | **[X]ms** | **[X]ms** | **[X] req/s** | **[X]%** |

**Percentiles:**
- 50% (Mediana): [X]ms
- 90%: [X]ms
- 95%: [X]ms
- 99%: [X]ms

**Análisis:**
- ✅ Tiempo de respuesta promedio: [ANÁLISIS]
- ✅ Tasa de error: [ANÁLISIS]
- ✅ Throughput: [ANÁLISIS]
- ✅ Conclusión: [CONCLUSIÓN]

### 💬 Notificaciones de Slack

**Notificación de Éxito:**
- ✅ Mensaje recibido en #jenkins-notifications
- ✅ Color: Verde (good)
- ✅ Contenido: Build exitoso con enlaces a reportes
- ✅ Timestamp: [HORA]

**Notificación de Error (si aplica):**
- ❌ Mensaje recibido en #jenkins-notifications
- ❌ Color: Rojo (danger)
- ❌ Contenido: Detalles del error y enlace a logs
- ❌ Timestamp: [HORA]

---

## 5. PROBLEMAS ENCONTRADOS Y SOLUCIONES

### Problema 1: [SI HUBO ALGÚN ERROR]
- **Descripción:** [DETALLES]
- **Causa:** [CAUSA RAÍZ]
- **Solución:** [CÓMO SE RESOLVIÓ]

### Problema 2: [SI HUBO OTRO ERROR]
- **Descripción:** [DETALLES]
- **Causa:** [CAUSA RAÍZ]
- **Solución:** [CÓMO SE RESOLVIÓ]

---

## 6. CONCLUSIONES

### Logros Alcanzados
- ✅ Pipeline de CI/CD completamente funcional
- ✅ Integración exitosa de 4 herramientas (Jenkins, SonarCloud, JMeter, Slack)
- ✅ Automatización del proceso de build, análisis y pruebas
- ✅ Notificaciones automáticas funcionando
- ✅ Detección de code smells y problemas de calidad
- ✅ Pruebas de carga con resultados satisfactorios

### Beneficios del Pipeline
1. **Automatización:** Reduce tiempo manual de testing
2. **Calidad:** Detecta problemas antes de producción
3. **Visibilidad:** Notificaciones inmediatas del estado
4. **Rendimiento:** Valida que la aplicación soporte carga
5. **Documentación:** Reportes automáticos de cada build

### Mejoras Futuras
- Agregar Quality Gate de SonarCloud
- Implementar tests unitarios con cobertura
- Configurar despliegue automático
- Agregar más escenarios de prueba en JMeter
- Integrar análisis de seguridad (OWASP)

---

## 7. ANEXOS

### Archivos del Proyecto
- `Jenkinsfile` - Definición del pipeline
- `jmeter-test-plan.jmx` - Plan de pruebas de carga
- `pom.xml` - Configuración de Maven
- `GUIA-COMPLETA-RETO.md` - Documentación técnica

### Enlaces
- **Repositorio:** https://github.com/VidalLuyo/proyectobase
- **SonarCloud:** https://sonarcloud.io/project/overview?id=VidalLuyo_proyectobase
- **Jenkins:** http://localhost:8080

### Comandos Útiles
```bash
# Ejecutar localmente
mvn clean install
mvn spring-boot:run

# Análisis de SonarCloud
mvn sonar:sonar -Dsonar.projectKey=VidalLuyo_proyectobase -Dsonar.organization=vidalluyo

# JMeter en línea de comandos
jmeter.bat -n -t jmeter-test-plan.jmx -l results.jtl -e -o report
```

---

**Documento preparado por:** Jesús Vidal Luyo  
**Fecha de entrega:** [FECHA]  
**Versión:** 1.0
