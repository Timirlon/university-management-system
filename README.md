# University Management System

A comprehensive Java-based application designed to streamline and automate the administrative and academic operations of universities. This system encompasses functionalities such as managing teacher and student databases, handling fee structures, processing examination results, and more.

## How to Run the Project

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Timirlon/university-management-system.git
   cd university-management-system
   ```

2. **Build the Project:**

   Ensure you have [Maven](https://maven.apache.org/) installed. Then, build the project using:

   ```bash
   ./mvnw clean install
   ```

3. **Run the Application:**

   Start the Spring Boot application with:

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the Application:**

   Once running, the application will be accessible at `http://localhost:8080`.

## Project Structure Overview

```
src/main/
├──java/org/exampleuniversity-management-system/
    ├── config/             # Configuration files for Spring Security
    ├── controller/         # API Controllers
    ├── dto/                # Data Transfer Objects
    ├── exception/          # Exception files and Error Handling
    ├── model/              # JPA Entities
    ├── repository/         # Spring Data JPA Repositories
    ├── security/           # Security model and service
    ├── service/            # Business logic and services
    ├── util/               # Utilitary classes
    └──UniversityManagementSystemApplication.java  # Entrypoint
└── resources/
    └── application.properties  

```

## Example API Endpoints

The application provides wide range of functionality by implementing the following endpoints:

- **User Management:**
  - `GET /users` - Retrieve a list of all users
  - `GET /users/{id}` - Retrieve details of a specific users by id
  - `POST /users` - Add a new user to database
  - `PATCH /users/{id}` - Update user credentials
  - `PATCH /users/role/{id}` - Update user role (e.g. STUDENT, TEACHER, ADMIN)
  - `DELETE /students/{id}` - Remove a student by id from system

- **Course Management:**
  - `GET /courses` - Retrieve a list of all courses
  - `GET /users/{id}` - Retrieve details of a specific course by id
  - `POST /courses` - Add a new course
  - `PATCH /courses/{id}` - Update course information
  - `DELETE /courses/{id}` - Remove a course

- **Enrollment:**
  - `GET /enrollments` - Retrieve all enrollments
  - `GET /enrollments/{id}` - Retrieve details of a specific enrollment by id
  - `POST /enrollments` - Enroll a student in a course
  - `PATCH /enrollments/{id}` - Update enrollment information
  - `DELETE /enrollments/{id}` - Remove an enrollment

## Used Technologies

• Java 21 - Main programming language
• Spring Boot 3 - Web-development framework
• Spring Security - Authentication and Authorization
• Spring Data JPA - Database interaction management
• PostgreSQL - Relational DBMS
• MapStruct - To Simplify Dto mapping
• Lombok - Provided helpful annotations
• JWT (Json Web Token) - Authentication Token

## Build and Run Commands

- **Build the Project:**

  ```bash
  ./mvnw clean install
  ```

- **Run the Application:**

  ```bash
  ./mvnw spring-boot:run
  ```


## Contact Information

For questions, suggestions, or contributions, please contact:

**Temirlan Turgimbayev**  
Email: [tturgimbayev@gmail.com]  
GitHub: [Timirlon](https://github.com/Timirlon)