# proyectobase

Proyecto Spring Boot con API REST para gestión de productos.

## Requisitos
- Java 11 o superior
- Maven 3.6+

## Comandos Maven

### Compilar y empaquetar el proyecto
```bash
mvn clean install
```

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación se ejecutará en: `http://localhost:8081`

## Endpoints disponibles
- `GET /api/products` - Listar todos los productos
- `GET /api/products/{id}` - Obtener producto por ID
- `POST /api/products` - Crear nuevo producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

## Uso en Jenkins
El proyecto está configurado para ejecutarse en Jenkins usando los siguientes comandos:
1. `mvn clean install` - Compila, ejecuta tests y genera el artefacto
2. `mvn spring-boot:run` - Inicia la aplicación Spring Boot