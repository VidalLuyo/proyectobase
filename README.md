# proyectobase

Proyecto Spring Boot con pipeline de CI/CD completo usando Jenkins, SonarQube, JMeter y Slack.

## 🚀 INICIO RÁPIDO

### Requisitos
- Java 11+
- Maven 3.6+
- Jenkins
- SonarQube o SonarCloud
- Apache JMeter
- Slack

### Ejecutar el pipeline
1. Configura las herramientas (ver `GUIA-COMPLETA-RETO.md`)
2. En Jenkins: **Build Now**
3. Observa las notificaciones en Slack

## 📋 PIPELINE DE CI/CD

El pipeline incluye las siguientes etapas:

1. **📥 Checkout** - Obtiene el código del repositorio
2. **🔨 Build & Install** - `mvn clean install`
3. **🔍 SonarQube Analysis** - Analiza calidad del código
4. **🚀 Start Application** - `mvn spring-boot:run`
5. **⚡ JMeter Load Tests** - Pruebas de carga (50-100 usuarios)
6. **📦 Verify Artifact** - Verifica el JAR generado
7. **💬 Slack Notifications** - Notifica éxito o error

## 🌐 Endpoints disponibles

### GET /products
```bash
curl http://localhost:8081/products
```

### POST /login
```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```

### GET /ping
```bash
curl http://localhost:8081/ping
```

## 🧪 Pruebas de carga con JMeter

### Configuración actual:
- **Usuarios concurrentes:** 50
- **Ramp-up time:** 10 segundos
- **Iteraciones:** 10 por usuario
- **Total peticiones:** 1000

### Ejecutar manualmente:
```bash
cd C:\apache-jmeter\bin
jmeter.bat -n -t jmeter-test-plan.jmx -l results.jtl -e -o report
```

## 📊 Ver resultados

### Jenkins
- Pipeline: http://localhost:8080
- Reporte JMeter: Build → JMeter Report

### SonarQube
- **SonarCloud:** https://sonarcloud.io
- **Local:** http://localhost:9000

### Slack
- Canal: `#jenkins-notifications`
- Notificaciones automáticas de builds

## 🔍 Análisis de código

SonarQube detectará:
- ❌ Variables no utilizadas
- ❌ Complejidad ciclomática alta
- ❌ Code smells
- ❌ Vulnerabilidades potenciales

## 📁 Estructura del proyecto

```
proyectobase/
├── src/main/java/vallegrande/edu/pe/
│   ├── controller/
│   │   └── ApiController.java          # Endpoints REST
│   ├── model/
│   │   ├── Product.java                # Modelo de producto
│   │   ├── LoginRequest.java           # Request de login
│   │   └── LoginResponse.java          # Response de login
│   ├── service/
│   │   └── ProductService.java         # Lógica de negocio
│   └── DemoApplication.java            # Clase principal
├── src/main/resources/
│   └── application.properties          # Configuración (puerto 8081)
├── Jenkinsfile                         # Pipeline de CI/CD
├── jmeter-test-plan.jmx               # Plan de pruebas JMeter
├── pom.xml                            # Dependencias Maven
├── GUIA-COMPLETA-RETO.md              # 📖 Guía detallada paso a paso
└── README.md                          # Este archivo
```

## 🛠️ Comandos útiles

### Compilar y ejecutar
```bash
mvn clean install
mvn spring-boot:run
```

### Análisis con SonarQube
```bash
# SonarQube Local
mvn sonar:sonar -Dsonar.projectKey=proyectobase

# SonarCloud
mvn sonar:sonar \
  -Dsonar.projectKey=TU_ORG_proyectobase \
  -Dsonar.organization=TU_ORG \
  -Dsonar.host.url=https://sonarcloud.io
```

### Ejecutar JMeter
```bash
# Interfaz gráfica
jmeter.bat

# Línea de comandos
jmeter.bat -n -t jmeter-test-plan.jmx -l results.jtl -e -o report
```

## 📖 Documentación completa

Para instrucciones detalladas paso a paso, consulta:
**[GUIA-COMPLETA-RETO.md](GUIA-COMPLETA-RETO.md)**

Incluye:
- ✅ Configuración de todas las herramientas
- ✅ Solución de problemas comunes
- ✅ Checklist de verificación
- ✅ Modificación de configuraciones

## 🎯 Resultados esperados

### JMeter
- Tiempo de respuesta: < 100ms
- Throughput: 100-200 req/seg
- Tasa de error: 0%

### SonarQube
- Code smells detectados: ~3
- Bugs: 0
- Vulnerabilidades: 0

### Slack
- Notificación de build exitoso
- Link a reportes
- Duración del build

## 🔧 Solución de problemas

Ver sección completa en: [GUIA-COMPLETA-RETO.md](GUIA-COMPLETA-RETO.md#-solución-de-problemas)

## 📝 Notas

- El código incluye **code smells intencionales** para demostrar el análisis de SonarQube
- Las credenciales de login son: `admin` / `password`
- El puerto por defecto es: `8081`

---

**Desarrollado para el reto de Integración Continua** 🚀