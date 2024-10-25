// Get list of all cars 
const getAllCars = async () => {
    const response = await fetch(`http://localhost:8080/cars`);
    const data = response.json();
}

// Post a car 