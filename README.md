# Car Inspection App

This application is designed for car inspectors to report and manage car inspection results efficiently. The app allows inspectors to add new car inspections, update inspection criteria, and view detailed inspection reports with a clear status indication for each car.

## Features

### Core Functionality
1. **Car Inspection Management**:
   - **View Car List**: View the list of cars and their inspection statuses.
   - **Add New Car Inspection**: Begin a new inspection for a car by entering its car plate. If the car is already in the database, an error will be shown.
   - **Update Inspection Criteria**: Inspectors can mark criteria as passed or failed. Failed criteria require a note.
   - **Inspection Status Tracking**: The app displays inspection status based on criteria results:
     - `0` - Not inspected yet (No criteria met)
     - `1` - Inspecting (Between 1-4 criteria met)
     - `2` - Inspected (All criteria met)

2. **Inspection Criteria**:
   - Each inspection includes up to 5 predefined criteria, each with:
     - **Is_good**: A boolean field indicating if the criterion passed.
     - **Note**: Required if `Is_good` is `false`.
   - Inspectors can modify criteria as needed.

### Pages

- **Page 1: Car Inspection Results**
   - View previously inspected cars, showing each car’s inspection status and results for individual criteria.
   - Failed criteria display an attached note for additional details.
   - Add a new car inspection to the system.
  
- **Page 2: Report Car Inspection**
   - Enter a car’s plate number to add a new car for inspection.
   - Update inspection criteria and add notes for any criteria that fail.
   - Submit the inspection report once all criteria have been reviewed.

## Technology Stack

- **Backend**: Java (Spring Boot) with MySQL database
  - API endpoints support car data retrieval, creation, and update for inspection purposes.
- **Frontend**: React for interactive inspection management.

## Setup and Usage

### Prerequisites

**Backend Requirements**:
- Java 21
- Gradle for dependency management
- An IDE (e.g., IntelliJ IDEA)
- Dependencies: Lombok, Spring Web, Spring Data JPA, MySQL Driver

**Frontend Requirements**:
- Node.js (for running the React app)
- Preferred text editor (e.g., VS Code)

### Installation

1. **Backend Setup**:
   - Clone the repository.
   - Open the project in IntelliJ IDEA and configure it to use IntelliJ for building and running.
   - Run `CarInspectionApplication` to start the API on `http://localhost:8080`.

2. **Frontend Setup**:
   - In the `frontend` directory, run `npm install` to install dependencies.
   - Start the React app with `npm start`.

## API Endpoints

### Page 1: Car Inspection Results
- **GET /cars**: Retrieve the list of cars and their inspection statuses.
  - **Endpoint**: `http://localhost:8080/cars`

### Page 2: Report Car Inspection
- **POST /car-inspections/{carName}/{id}?isGood={isGood}&note={note}**: Add a new inspection result for a specific car, with inspection criteria details.
  - **Endpoint**: `http://localhost:8080/car-inspections/${carName}/${id}?isGood=${isGood}&note=${encodeURIComponent(note)}`
  - **Parameters**:
    - `carName`: The name or plate number of the car.
    - `id`: Inspection criterion ID.
    - `isGood`: Boolean indicating if the criterion was met.
    - `note`: Description of the issue (required if `isGood` is false).

- **GET /cars/{carName}**: Retrieve the inspection results for a specific car.
  - **Endpoint**: `http://localhost:8080/cars/${carName}`
  - **Parameter**:
    - `carName`: The name or plate number of the car.
