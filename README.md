# Student Project Monitoring System

This project is a web-based interactive project monitoring system designed to facilitate student-lecturer collaboration. It allows tracking and management of student projects with features such as project status updates, task management, and progress reporting.

## Features

- **Project Details Management**: View project information, latest tasks, and comments.
- **Status Update**: Approve or decline student projects.
- **Task Management**: View the latest task assigned to the project.
- **Comment Management**: View the latest comments on the project.
- **File Management**: Download project files.
- **Progress Reporting**: View or create progress reports for student projects.

## Technologies Used

### Frontend

- [React.js](https://reactjs.org/): A JavaScript library for building user interfaces.
- [Tailwind CSS](https://tailwindcss.com/): A utility-first CSS framework for styling the UI.

### Backend

- [Spring Boot](https://spring.io/projects/spring-boot): A Java-based framework used to create standalone, production-grade applications.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Used for data persistence and database management.
- [Spring Security](https://spring.io/projects/spring-security): Provides authentication and authorization capabilities.

### Database

- [MySQL](https://www.mysql.com/): A relational database management system used to store project data.

### Other Libraries

- [Axios](https://axios-http.com/): A promise-based HTTP client for making API requests.
- [React Router](https://reactrouter.com/): A library for routing in React applications.

## Installation

### Prerequisites

- [Node.js](https://nodejs.org/): Ensure Node.js and npm are installed on your system.
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html): Required for running Spring Boot.
- [MySQL](https://dev.mysql.com/downloads/installer/): Set up MySQL for the backend.

### Backend Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/ralphses/project-monitoring-app.git
   ```
2. **Configure MySQL Database**
   - Create a database named student_project_monitoring
   - Update application.yml with your database credentials.

3. **Build and Run the Backend**
    ```bash
   ./mvnw spring-boot:run
    ```

### Frontend Setup
1. **Navigate to the Frontend Directory**
    ```bash
   cd frontend 
   ```
2. **Install Dependencies**
    ```bash
   npm instal
   ```

3. **Start the React Application**
    ```bash
   npm start 
   ```
