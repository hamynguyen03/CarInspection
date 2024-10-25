import React, { useState } from 'react';

export const NewCarForm = ({ setCarName, setIsCarAdded }) => {
  const [value, setValue] = useState('');
  const [apiResponse, setApiResponse] = useState(''); 
  const [isCarAdded, setLocalCarAdded] = useState(false); 

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (value) {
      try {
        // POST request to the API
        const response = await fetch('http://localhost:8080/cars', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ name: value }), 
        });

        if (!response.ok) {
          const contentType = response.headers.get('content-type');
          let errorMessage = 'An unexpected error occurred.'; 

          if (contentType && contentType.includes('application/json')) {
            // Handle JSON error response
            const errorData = await response.json();
            errorMessage = errorData.message || 'An error occurred while adding the car.';
          } else {
            // Handle non-JSON error response (plain text)
            errorMessage = await response.text();
          }

          if (response.status === 400) {
            errorMessage = 'Car with this name already exists!';
          } else if (response.status >= 500) {
            errorMessage = 'Server is down or unavailable. Please try again later.';
          }

          throw new Error(errorMessage); 
        }

        setCarName(value); 
        setApiResponse('Car added successfully!'); 
        setIsCarAdded(true); 
        setLocalCarAdded(true); 
      } catch (error) {
        console.error('Caught Error:', error.message); 
        setApiResponse(error.message); 
        setIsCarAdded(false); 
        setLocalCarAdded(false); 
      }
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="NewCarForm">
        <input
          type="text"
          value={value}
          onChange={(e) => setValue(e.target.value)}
          className="criteria-input"
          placeholder="Car name"
          disabled={isCarAdded} 
        />
        {/* Conditionally render the button based on isCarAdded */}
        {!isCarAdded && (
          <button type="submit" className="criteria-btn">
            Report Inspection
          </button>
        )}
      </form>

      {/* Conditionally render the API response directly under the form */}
      {apiResponse && (
        <p className="api-response">
          {apiResponse}
        </p>
      )}

      {/* Conditionally render the message if a car is added successfully */}
      {isCarAdded && (
        <p className="message">
          Please select all UNSATISFYING criteria
        </p>
      )}
    </div>
  );
};
