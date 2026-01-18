# Spring Contacts API

A Spring Boot reactive REST API for managing contacts with comprehensive security, multiple environment profiles, and OpenAPI/Swagger documentation.

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Configuration Profiles](#configuration-profiles)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [API Documentation](#api-documentation)
- [Security](#security)
- [Database](#database)
- [Docker Deployment](#docker-deployment)
- [Troubleshooting](#troubleshooting)

## 📖 Overview

The Spring Contacts API is a reactive Spring Boot application that provides RESTful endpoints for creating, reading, and searching contact information. It features API key authentication, multi-environment support, and comprehensive validation.

**Repository**: https://github.com/munwarvh/spring-contacts-api

## 🛠 Tech Stack

- **Java**: 11
- **Framework**: Spring Boot 2.6.7
- **Reactive Stack**: Spring WebFlux (Reactor)
- **Database**: 
  - H2 (embedded, for local/testing)
  - PostgreSQL (for dev/qa/prod)
- **ORM**: Spring Data JPA with Hibernate
- **API Documentation**: SpringDoc OpenAPI 3
- **Security**: Custom API Key Authentication
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, Reactor Test
- **Code Coverage**: JaCoCo
- **Mutation Testing**: PIT
- **Containerization**: Docker & Docker Compose

## ✨ Features

- 🔐 **API Key Authentication** - Secure access with custom API key validation
- 🔄 **Reactive Programming** - Built with Spring WebFlux for non-blocking operations
- 📝 **CRUD Operations** - Complete contact management (Create, Read, Update, Search)
- 🔍 **Advanced Search** - Search by firstname, lastname, SSN, or IBAN
- 🌍 **Multi-Environment Support** - Separate profiles for local, dev, qa, and prod
- 📚 **OpenAPI/Swagger** - Interactive API documentation
- ✅ **Comprehensive Validation** - Bean validation on all inputs
- 🗄️ **Database Flexibility** - H2 for quick local testing, PostgreSQL for production
- 🐳 **Docker Support** - Containerized deployment with Docker Compose
- 🧪 **High Test Coverage** - Unit tests, integration tests, and mutation testing
- 📊 **H2 Console** - Database inspection tool (local profile only)
- 🔧 **Debug Endpoints** - Development endpoints for database inspection

## 📦 Prerequisites

- **Java Development Kit (JDK)**: 11 or higher
- **Maven**: 3.6+ (or use the Maven wrapper included)
- **Docker** (optional): For containerized deployment
- **PostgreSQL** (optional): For dev/qa/prod profiles
- **cURL** or **Postman**: For API testing

## 📁 Project Structure

```
contacts/
├── src/
│   ├── main/
│   │   ├── java/nl/craftsmen/
│   │   │   ├── contacts/          # Contact domain logic
│   │   │   ├── security/          # Security configuration & API key auth
│   │   │   └── exceptionhandling/ # Global exception handlers
│   │   └── resources/
│   │       ├── application.yml               # Base configuration
│   │       ├── application-local.yml         # Local (H2) profile
│   │       ├── application-dev.yml           # Development (PostgreSQL)
│   │       ├── application-qa.yml            # QA (PostgreSQL)
│   │       ├── application-prod.yml          # Production (PostgreSQL)
│   │       └── schema.sql                    # Database schema
│   └── test/                      # Unit and integration tests
├── Dockerfile                     # Docker image definition
├── docker-compose.yml             # Docker Compose configuration
├── pom.xml                        # Maven project configuration
├── test-complete.bat              # Complete test script (Windows)
├── test-all-endpoints.bat         # API endpoint test script
└── README.md                      # This file
```

## ⚙️ Configuration Profiles

The application supports multiple profiles for different environments:

### 🏠 Local Profile (default)
- **Database**: H2 in-memory database
- **Port**: 8081
- **API Key**: `local-test-api-key-12345`
- **H2 Console**: Enabled at `/h2-console`
- **Swagger UI**: Enabled
- **Use Case**: Quick local development and testing without external dependencies

### 🔧 Dev Profile
- **Database**: PostgreSQL on `localhost:5432/contacts_dev`
- **Port**: 8081
- **API Key**: `dev-api-key-67890` (or set via `CONTACTS_APIKEY` env variable)
- **Swagger UI**: Enabled
- **DDL**: update (auto-update schema)
- **Use Case**: Development environment with persistent database

### 🧪 QA Profile
- **Database**: PostgreSQL (configured via environment variables)
- **Port**: 8081
- **API Key**: Set via `CONTACTS_APIKEY` environment variable
- **Swagger UI**: Enabled
- **DDL**: validate (schema must exist)
- **Use Case**: Quality assurance and testing environment

### 🚀 Prod Profile
- **Database**: PostgreSQL (configured via environment variables)
- **Port**: 8081
- **API Key**: **Required** via `CONTACTS_APIKEY` environment variable
- **Swagger UI**: Disabled
- **API Docs**: Disabled
- **DDL**: validate (schema must exist)
- **Connection Pool**: Optimized with HikariCP
- **Use Case**: Production deployment

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd contacts
```

### 2. Build the Application

```bash
mvn clean install
```

This will:
- Compile the source code
- Run all unit tests (40 tests)
- Generate test reports
- Create the JAR file in `target/contacts-0.0.1-SNAPSHOT.jar`

### 3. Skip Tests (Optional)

If you want to build without running tests:

```bash
mvn clean install -DskipTests
```

## 🏃 Running the Application

### Option 1: Run with Maven (Local Profile - Default)

```bash
mvn spring-boot:run
```

### Option 2: Run as JAR (Local Profile)

```bash
java -jar target/contacts-0.0.1-SNAPSHOT.jar
```

### Option 3: Run with Specific Profile

**Dev Profile:**
```bash
java -jar target/contacts-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

**QA Profile:**
```bash
java -jar target/contacts-0.0.1-SNAPSHOT.jar --spring.profiles.active=qa
```

**Prod Profile:**
```bash
export CONTACTS_APIKEY=your-secure-api-key-here
export DB_HOST=your-db-host
export DB_PORT=5432
export DB_NAME=contacts_prod
export DB_USERNAME=your-db-user
export DB_PASSWORD=your-db-password
java -jar target/contacts-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Verify Application is Running

```bash
curl http://localhost:8081/actuator/health
```

Expected response: `{"status":"UP"}`

## 🧪 Testing

### Run All Unit Tests

```bash
mvn test
```

**Output**: 40 tests covering all components
- Contact domain logic
- Security and API key authentication
- Exception handling
- Service layer
- Controller layer

### Test Coverage Report

```bash
mvn clean test jacoco:report
```

Report available at: `target/site/jacoco/index.html`

### Mutation Testing

```bash
mvn clean test pitest:mutationCoverage
```

Report available at: `target/pit-reports/index.html`

### Run Integration Tests

```bash
mvn verify
```

### Test Complete Workflow (Windows)

The project includes a comprehensive test script that:
1. Checks application health
2. Verifies database connection
3. Creates multiple contacts
4. Views all contacts
5. Displays final count

**Run the script:**
```bash
test-complete.bat
```

**What it tests:**
- ✅ Application health endpoint
- ✅ Database count endpoint
- ✅ POST /contacts (Create contact)
- ✅ Debug endpoint to view all contacts
- ✅ Database persistence verification

### Manual API Testing with cURL

**Create a Contact:**
```bash
curl -X POST "http://localhost:8081/contacts" ^
  -H "Content-Type: application/json" ^
  -H "X-API-KEY: local-test-api-key-12345" ^
  -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address1\":\"123 Main St\",\"zipcode\":\"12345\",\"city\":\"Amsterdam\",\"iban\":\"NL91ABNA0417164300\",\"dateOfBirth\":\"1990-01-15\",\"email\":\"john@example.com\",\"phone\":\"+31-20-1234567\"}"
```

**Get Contact by ID:**
```bash
curl -H "X-API-KEY: local-test-api-key-12345" http://localhost:8081/contacts/1
```

**Search Contacts:**
```bash
curl -H "X-API-KEY: local-test-api-key-12345" "http://localhost:8081/contacts?firstname=John"
```

**View All Contacts (Debug - Local Only):**
```bash
curl http://localhost:8081/debug/contacts/all
```

**Get Contact Count (Debug - Local Only):**
```bash
curl http://localhost:8081/debug/contacts/count
```

## 📚 API Documentation

### Swagger UI (Interactive Documentation)

Once the application is running, access Swagger UI at:

```
http://localhost:8081/webjars/swagger-ui/index.html
```

**Note**: Swagger is disabled in production profile for security reasons.

### Available Endpoints

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| POST | `/contacts` | Create a new contact | API Key Required |
| GET | `/contacts/{id}` | Get contact by ID | API Key Required |
| GET | `/contacts?firstname=&lastname=&ssn=&iban=` | Search contacts | API Key Required |
| GET | `/actuator/health` | Health check | None |
| GET | `/h2-console` | H2 Database console (local only) | None |
| GET | `/debug/contacts/all` | View all contacts (debug) | None |
| GET | `/debug/contacts/count` | Get contact count (debug) | None |

### Sample Contact JSON

```json
{
  "firstName": "Alice",
  "lastName": "Johnson",
  "address1": "789 Pine Street",
  "address2": "Apt 4B",
  "zipcode": "67890",
  "city": "Utrecht",
  "country": "Netherlands",
  "iban": "NL02RABO0123456789",
  "dateOfBirth": "1992-05-10",
  "email": "alice@example.com",
  "phone": "+31-30-9876543",
  "ssn": "123-45-6789"
}

```

**Required Fields:**
- firstName
- lastName
- address1
- zipcode
- city
- iban
- dateOfBirth

## 🔐 Security

### API Key Authentication

All endpoints (except health and debug endpoints) require API key authentication using the `X-API-KEY` header.

**Header Example:**
```
X-API-KEY: local-test-api-key-12345
```

### API Keys by Profile

| Profile | Default API Key | Configuration |
|---------|----------------|---------------|
| Local | `local-test-api-key-12345` | Hardcoded in config |
| Dev | `dev-api-key-67890` | Can override with `CONTACTS_APIKEY` |
| QA | None | **Required**: Set `CONTACTS_APIKEY` env variable |
| Prod | None | **Required**: Set `CONTACTS_APIKEY` env variable |

### Security Features

- ✅ Custom reactive API key authentication
- ✅ Request/response logging
- ✅ Input validation with Bean Validation
- ✅ Global exception handling
- ✅ No credentials in logs
- ✅ Secure production configuration

## 🗄️ Database

### H2 Console (Local Profile Only)

Access the H2 database console at:

```
http://localhost:8081/h2-console
```

**Connection Details:**
- **JDBC URL**: `jdbc:h2:mem:contactsdb`
- **Username**: `sa`
- **Password**: (leave empty)
- **Driver**: `org.h2.Driver`

### PostgreSQL Setup (Dev/QA/Prod)

**For Dev Profile:**

```bash
# Create database
createdb contacts_dev

# Or using psql
psql -U postgres
CREATE DATABASE contacts_dev;
```

**For Production:**

Set the following environment variables:
```bash
export DB_HOST=your-database-host
export DB_PORT=5432
export DB_NAME=contacts_prod
export DB_USERNAME=your-username
export DB_PASSWORD=your-password
```

### Database Schema

The application automatically manages the database schema:
- **Local**: `create-drop` (recreates on each restart)
- **Dev**: `update` (auto-updates schema)
- **QA/Prod**: `validate` (schema must exist and match)

## 🐳 Docker Deployment

### Build Docker Image

```bash
# First, build the JAR
mvn clean package -DskipTests

# Build Docker image
docker build -t contacts .
```

### Run with Docker Compose

The project includes a `docker-compose.yml` that sets up:
- Contacts API application
- PostgreSQL database
- Network configuration
- Health checks

**Prerequisites:**
1. Create `.env_contacts` file:
```properties
SPRING_PROFILES_ACTIVE=dev
CONTACTS_APIKEY=your-api-key
DB_HOST=contacts_db
DB_PORT=5432
DB_NAME=contacts_dev
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

2. Create `.env_postgres` file:
```properties
POSTGRES_DB=contacts_dev
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

**Start Services:**
```bash
docker-compose up -d
```

**Check Logs:**
```bash
docker-compose logs -f contacts
```

**Stop Services:**
```bash
docker-compose down
```

### Access the Application

- **API**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/webjars/swagger-ui/index.html
- **Health Check**: http://localhost:8081/actuator/health
- **Database**: localhost:3001 (mapped from container's 5432)

## 🔧 Troubleshooting

### Issue: Tests Failing with Mockito Strict Stubbing Error

**Solution**: Ensure test mocks use the correct constant values (e.g., `X-API-KEY` header name)

### Issue: Cannot Access H2 Console

**Symptoms**: H2 console shows blank page or 404
**Solution**: 
- Ensure you're running with `local` profile
- Access via: `http://localhost:8081/h2-console`
- Check JDBC URL is: `jdbc:h2:mem:contactsdb`

### Issue: API Returns 401 Unauthorized

**Symptoms**: All API calls return 401
**Solution**: 
- Ensure you're sending the `X-API-KEY` header
- Verify the API key matches the profile configuration
- Check logs for "No API Key found in request headers"

### Issue: Port 8081 Already in Use

**Solution**:
```bash
# Find process using port 8081 (Windows)
netstat -ano | findstr :8081

# Kill the process
taskkill /PID <process-id> /F
```

### Issue: PostgreSQL Connection Failed

**Solution**:
- Verify PostgreSQL is running: `pg_isready -U postgres`
- Check connection details in application-{profile}.yml
- Ensure database exists: `psql -U postgres -l`
- Verify credentials and environment variables

### Issue: Application Starts but Endpoints Return Errors

**Solution**:
- Check logs in console for detailed error messages
- Verify database connection and schema
- Ensure proper profile is active
- Check if all required environment variables are set

### Issue: Build Fails

**Solution**:
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests if needed to get a clean build
mvn clean install -DskipTests
```

## 📝 Development Notes

### Adding New Endpoints

1. Create controller in `nl.craftsmen.contacts` package
2. Add service layer logic
3. Update OpenAPI annotations
4. Write unit tests
5. Add to this README

### Modifying Security

API key validation is in `nl.craftsmen.security.apikey.ApiKeyValidator`

### Changing Database Schema

Update `src/main/resources/schema.sql` and entity classes

## 📄 License

This project is for demonstration purposes.

## 👥 Contributors

Developed by munwarvh

---

## 🚀 Quick Start Summary

```bash
# 1. Build
mvn clean install

# 2. Run (Local with H2)
java -jar target/contacts-0.0.1-SNAPSHOT.jar

# 3. Test
test-complete.bat

# 4. Access
# - Swagger: http://localhost:8081/webjars/swagger-ui/index.html
# - H2 Console: http://localhost:8081/h2-console
# - API: http://localhost:8081/contacts (with X-API-KEY header)
```

**Happy Coding! 🎉**
