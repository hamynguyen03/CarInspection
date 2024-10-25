# Car Inspection App

This project consists of two parts:
1. **Backend**: A Spring Boot application running on Java, which serves the API for car inspections.
2. **Frontend**: A React application that allows users (car owners) to interact with the backend to view their car inspection results and report new inspections.

## Prerequisites for backend 

### Backend Setup (Spring Boot)
- Java 21
- Gradle (to manage dependencies)
- IntelliJ IDEA (or any preferred IDE)
- Required dependencies: Lombok, Spring Web, Spring Data JPA, My SQL Driver 

**Open Project in IntelliJ**:
   - Open IntelliJ IDEA.
   - Select "Open" from the welcome screen.
   - Navigate to the cloned project folder and select it.

**Run the Backend**:
   - Create a new Application.
   - Right-click and select `Run 'CarInspectionApplication'`.
   - This will start the backend API on `http://localhost:8080`.
   - If there is a problem running the application, go to Setting -> Buid, Execution, Deployment -> Buid Tools -> Gradle -> Build and Run -> Select ItelliJ IDEA for both "Build and run using:" and "Run tests using:".
