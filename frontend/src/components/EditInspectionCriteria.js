import React, {useState} from 'react'

export const EditInspectionCriteria = ({editCriteria, criteria}) => {
    const [value, setValue] = useState(criteria.criteria);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault(); 
        setIsLoading(true); 
        setError(null); 
    
        try {
          // PUT request to update the criteria description
          const response = await fetch(`http://localhost:8080/inspection-criteria/${criteria.id}/description`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ description: value }), 
          });
    
          if (!response.ok) {
            throw new Error('Failed to update inspection criteria'); 
          }
    
          editCriteria(value, criteria.id); 
        } catch (err) {
          console.error('Error:', err); 
          setError('Failed to update inspection criteria. Please try again.'); 
        } finally {
          setIsLoading(false); 
        }
      };

      return (
        <form onSubmit={handleSubmit} className="NewCarForm">
          <input
            type="text"
            value={value}
            onChange={(e) => setValue(e.target.value)} 
            className="criteria-input"
            placeholder="Update criteria"
            disabled={isLoading} 
          />
          <button type="submit" className="criteria-btn" disabled={isLoading}>
            {isLoading ? 'Updating...' : 'Update'}
          </button>
          {error && <p className="error-message">{error}</p>} {/* Display error message*/}
        </form>
      );
}