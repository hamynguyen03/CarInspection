import React, { useState, useEffect } from "react";
import DetailWrapper from './DetailWrapper'; 
import { useNavigate } from "react-router-dom";

const carStatusMapping = {
  0: "Not Inspected",
  1: "Inspecting",
  2: "Inspected"
};

export const CarInspectionResultsPage = () => {
  const navigate = useNavigate();
  const [cars, setCars] = useState([]);
  const [selectedCar, setSelectedCar] = useState(null);

  // Fetch cars data when the component mounts
  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await fetch("http://localhost:8080/cars", {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });

        if (!response.ok) {
          throw new Error("Failed to fetch cars.");
        }

        const carData = await response.json(); 
        setCars(carData); // Set cars data

      } catch (error) {
        console.error("Error fetching cars:", error);
        alert("Error fetching cars: " + error.message);
      }
    };

    fetchCars();
  }, []);

  // Navigate to the "Add Inspection" page
  const handleAddInspection = () => {
    navigate('/carinspectionreport');
  };

  // Handle the car selection and fetch car-specific data
  const handleCarSelect = (e) => {
    const carId = e.target.value;
    const selectedCar = cars.find((car) => car.carId === parseInt(carId));
    setSelectedCar(selectedCar); // Set selected car
  };

  return (
    <div className="car-inspection-results">
      <h1>Car Inspection Results</h1>

      {/* Conditionally render the dropdown only if cars are available */}
      {cars.length > 0 && (
        <div className="dropdown-container">
          <label htmlFor="car-dropdown">Inspected Car: </label>
          <select id="car-dropdown" onChange={handleCarSelect} defaultValue="">
            <option value="" disabled>Select a car</option>
            {cars.map((car) => (
              <option key={car.carId} value={car.carId}>
                {car.name} - {carStatusMapping[car.status]}
              </option>
            ))}
          </select>
        </div>
      )}

      {/* Display selected car details */}
      {selectedCar && (
        <DetailWrapper> 
          <h2>Details for {selectedCar.name}</h2>
          <p>Status: {carStatusMapping[selectedCar.status]}</p>
          <h3>Inspections</h3>
          <ul>
            {selectedCar.inspections.length > 0 ? (
              selectedCar.inspections.map((inspection) => (
                <li key={inspection.carInspectionId}>
                  {inspection.inspectionCriteria.criteriaDesc}:{" "}
                  {inspection.good ? (
                    "Passed"
                  ) : (
                    <>
                      <span>Failed</span>
                      {inspection.note && (
                        <p className="inspection-note">
                          <strong>Note:</strong> {inspection.note}
                        </p>
                      )}
                    </>
                  )}
                </li>
              ))
            ) : (
              <p>No inspections found.</p>
            )}
          </ul>
        </DetailWrapper>
      )}

      {/* Add New Inspection button is always visible */}
      <button className="add-inspection-btn" onClick={handleAddInspection}>
        Add New Inspection
      </button>
    </div>
  );
};
