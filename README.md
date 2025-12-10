# Device Manager API

A Spring Boot REST API for managing device resources with full CRUD functionality, validations, persistence, containerization and documentation.

---

## Project Overview

This project provides a RESTful API to manage devices with the following attributes:

- Id
- Name
- Brand
- State (AVAILABLE, IN_USE, INACTIVE)
- Creation Time

The service supports creation, updates, retrieval, filtering and deletion of devices.

---

## Features

### Supported Functionalities

- Create a new device
- Update existing device (full & partial)
- Get device by ID
- Get all devices
- Filter devices by brand
- Filter devices by state
- Delete device
- Change device state

### 📁 Project Structure
```
DeviceManager/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
 ├── dto/
 ├── mapper/
 ├── exceptions/
 └── test/
```
### Example API Endpoints
#
#
| Method | Endpoint                         | Description         |
| ------ | -----------------------------    | ------------------- |
| POST   | `/api/device/add`                | Create device       |
| PUT    | `/api/device/update/{id}`        | Update device       |
| GET    | `/api/device/getById/{id}`       | Get device by ID    |
| GET    | `/api/device`                    | Get all devices     |
| GET    | `/api/device/getByBrand/{brand}` | Filter by brand     |
| GET    | `/api/device/getByState/{state}` | Filter by state     |
| DELETE | `/api/device/delete/{id}`        | Delete device       |
| POST   | `/api/device/changeState/{id}`   | Change device state |

---
### 📖 API Documentation (Swagger)

Once the app is running, access:

Swagger UI:
```
http://localhost:8080/swagger-ui.html
```
OpenAPI JSON:
```
http://localhost:8080/v3/api-docs
```


---

## Domain Validations

- Creation time cannot be updated
- Name and brand cannot be updated when device is IN_USE
- IN_USE devices cannot be deleted

---

## Tech Stack

- Java 21
- Spring Boot 3.x
- Maven 3.9+
- PostgreSQL
- Spring Data JPA
- Docker & Docker Compose
- Swagger (Springdoc OpenAPI)
- JUnit 5 + Mockito

---

## Configuration

### Environment Variables

```bash
DB_URL=jdbc:postgresql://localhost:5432/device_db
DB_USERNAME=user
DB_PASSWORD=postgres
```
