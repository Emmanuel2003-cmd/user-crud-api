# 👤 User CRUD API — Spring Boot

API REST para la gestión de usuarios (**C**reate, **R**ead, **U**pdate, **D**elete), construida con **Java 17** y **Spring Boot 3**.

Desarrollé este proyecto para **practicar Java con Spring Boot**. Apliqué el **patrón MVC** para organizar la arquitectura en capas y usé **DTOs** para controlar qué datos se exponen como JSON, sin exponer directamente la entidad de base de datos.

---

## 🛠️ Tecnologías

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

- Java 17 · Spring Boot 3 (Spring Web, Spring Data JPA, Bean Validation)
- PostgreSQL
- Docker y Docker Compose
- JUnit 5 y Mockito para pruebas unitarias
- Maven

---

## 🏗️ Arquitectura (MVC por capas)

```
Cliente ──► Controller ──► Service ──► Repository ──► PostgreSQL
              │               │
              ▼               ▼
            DTOs  ◄──────  Mapper  ◄──── Entity
```

```
src/main/java/com/emmanuel/usercrud
├── controller/   → Recibe las peticiones HTTP (/api/users)
├── service/      → Lógica de negocio (interfaz + implementación)
├── repository/   → Acceso a datos con Spring Data JPA
├── entity/       → Entidad JPA User (tabla users)
├── dto/          → UserRequestDTO (entrada) y UserResponseDTO (salida JSON)
├── mapper/       → Conversión Entity ⇄ DTO
└── exception/    → Excepciones personalizadas y manejo global de errores
```

---

## 📌 Endpoints

URL base: `http://localhost:8080/api/users`

| Operación | Método | Endpoint | Descripción | Respuesta |
|---|---|---|---|---|
| **READ** | `GET` | `/api/users` | Lista todos los usuarios | `200 OK` |
| **READ** | `GET` | `/api/users/{id}` | Obtiene un usuario por id | `200 OK` · `404 Not Found` |
| **CREATE** | `POST` | `/api/users` | Crea un usuario | `201 Created` · `400` · `409 Conflict` |
| **UPDATE** | `PUT` | `/api/users/{id}` | Actualiza un usuario | `200 OK` · `400` · `404` · `409` |
| **DELETE** | `DELETE` | `/api/users/{id}` | Elimina un usuario | `204 No Content` · `404` |

### Ejemplo — crear usuario

**Request** `POST /api/users`
```json
{
  "name": "Emmanuel Ocoro",
  "email": "emmanuel@example.com",
  "phone": "3001234567"
}
```

**Response** `201 Created`
```json
{
  "id": 1,
  "name": "Emmanuel Ocoro",
  "email": "emmanuel@example.com",
  "phone": "3001234567",
  "createdAt": "2026-10-27T22:30:00"
}
```

### Ejemplo — error de validación

**Response** `400 Bad Request`
```json
{
  "timestamp": "2026-10-27T22:31:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "fieldErrors": {
    "email": "El email no tiene un formato válido"
  }
}
```

### Validaciones
- `name`: obligatorio, máximo 100 caracteres
- `email`: obligatorio, formato válido y único
- `phone`: opcional, máximo 20 caracteres

---

## 🚀 Cómo ejecutarlo

### Opción 1: Docker Compose (recomendado)

Requisitos: Docker y Docker Compose.

```bash
git clone https://github.com/Emmanuel2003-cmd/user-crud-api.git
cd user-crud-api
docker compose up --build
```

Esto levanta **PostgreSQL** y la **API** en `http://localhost:8080`.

Para detenerlo:
```bash
docker compose down
```

### Opción 2: Local con Maven

Requisitos: Java 17+, Maven y una base de datos PostgreSQL con la base `users_db`.

```bash
mvn spring-boot:run
```

Puedes cambiar la conexión con las variables de entorno `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.

---

## 🧪 Pruebas

Pruebas unitarias de la capa de servicio con **JUnit 5** y **Mockito**:

```bash
mvn test
```

---

## 👨‍💻 Autor

**Emmanuel Ocoro Bonilla** — Desarrollador Backend · Estudiante de Ingeniería de Sistemas

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/emmanuelocoro/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)](https://github.com/Emmanuel2003-cmd)
