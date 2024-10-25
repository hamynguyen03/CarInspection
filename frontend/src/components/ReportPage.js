import React, { useState } from "react";
import { InspectionCriteria } from "./InspectionCriteria";
import { NewCarForm } from "./NewCarForm";
import { EditInspectionCriteria } from "./EditInspectionCriteria";
import { NoteForm } from "./NoteForm";
import Button from "./Button";
import { useNavigate } from "react-router-dom";

const carStatusMapping = {
  0: "Not inspected yet",
  1: "Inspecting",
  2: "Inspected"
};

export const ReportPage = () => {
  const [criterias, setCriterias] = useState([
    { id: 1, criteria: "Engine Performance", completed: false, isEditing: false, note: "", confirmed: false },
    { id: 2, criteria: "Tire Condition", completed: false, isEditing: false, note: "", confirmed: false },
    { id: 3, criteria: "Brake Condition", completed: false, isEditing: false, note: "", confirmed: false },
    { id: 4, criteria: "Headlights & Signals", completed: false, isEditing: false, note: "", confirmed: false },
    { id: 5, criteria: "Windshields & Wipers", completed: false, isEditing: false, note: "", confirmed: false }
  ]);

  const [carName, setCarName] = useState('');
  const [isCarAdded, setIsCarAdded] = useState(false);
  const navigate = useNavigate();

  // Toggle between complete and incomplete
  const toggleComplete = (id) => {
    setCriterias(
      criterias.map((criteria) => {
        if (!criteria.completed && criteria.id === id) {
          return { ...criteria, completed: true };
        }
        if (criteria.completed && criteria.id === id) {
          return { ...criteria, completed: false, note: "" };
        }
        return criteria;
      })
    );
  };

  // Handle note change
  const handleNoteChange = (e, id) => {
    const { value } = e.target;
    setCriterias(
      criterias.map((criteria) => 
        criteria.id === id ? { ...criteria, note: value } : criteria
      )
    );
  };

  // Edit Criteria action
  const editCriteriaAction = (id) => {
    setCriterias(
      criterias.map((criteria) => 
        criteria.id === id ? { ...criteria, isEditing: !criteria.isEditing } : criteria
      )
    );
  };

  // Edit Task (renaming the criteria)
  const editTask = (newCriteria, id) => {
    setCriterias(
      criterias.map((criteria) => 
        criteria.id === id ? { ...criteria, criteria: newCriteria, isEditing: !criteria.isEditing } : criteria
      )
    );
  };

  // Confirm Criteria (Save action)
  const confirmCriteria = async (id) => {
    const criteria = criterias.find((c) => c.id === id);
    const isGood = !criteria.completed; // If not completed, isGood = true
    const note = criteria.note;

    try {
      const response = await fetch(
        `http://localhost:8080/car-inspections/${carName}/${id}?isGood=${isGood}&note=${encodeURIComponent(note)}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          }
        }
      );

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error("Failed to save car inspection: " + errorText);
      }

      // Confirm the criteria after successful POST
      setCriterias(
        criterias.map((criteria) =>
          criteria.id === id ? { ...criteria, confirmed: true, isEditing: false } : criteria
        )
      );
    } catch (error) {
      console.error("Error during POST request:", error.message);
      alert("Error in saving car inspection: " + error.message);
    }
  };

  // Check if all criteria are confirmed
  const areAllCriteriaSaved = () => {
    return criterias.every((criteria) => criteria.confirmed);
  };

  // Fetch Car Inspection Result
  const handleInspectionResult = async () => {
    if (!areAllCriteriaSaved()) {
      alert("Please save all criteria before getting the car inspection result.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/cars/${carName}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json"
        }
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error("Failed to get car inspection result: " + errorText);
      }

      const carData = await response.json();
      const carStatus = carStatusMapping[carData.status];

      alert(`Your car inspection result is: ${carStatus}`);
      navigate("/");
    } catch (error) {
      console.error("Error in fetching car inspection result:", error);
      alert("Error in fetching car inspection result: " + error.message);
    }
  };

  return (
    <div className="ReportPage">
      <h1>Car Inspection Report</h1>
      <NewCarForm setCarName={setCarName} setIsCarAdded={setIsCarAdded} />

      {isCarAdded && (
        <>
          {criterias.map((criteria) =>
            criteria.isEditing ? (
              <EditInspectionCriteria 
                key={criteria.id} 
                editCriteria={editTask} 
                criteria={criteria} 
              />
            ) : (
              <div key={criteria.id}>
                <InspectionCriteria
                  key={criteria.id}
                  criteria={criteria}
                  editCriteria={editCriteriaAction}
                  toggleComplete={toggleComplete}
                  confirmCriteria={confirmCriteria}
                  isConfirmDisabled={!criteria.note.trim() && criteria.completed} // Disable if note is empty for completed tasks
                />
                {/* Always show the note form if the criteria is completed */}
                {criteria.completed && (
                  <NoteForm
                    note={criteria.note}
                    onNoteChange={handleNoteChange}
                    criteriaId={criteria.id} // Ensure correct note form
                    isDisabled={criteria.confirmed} // Disable if confirmed
                  />
                )}
              </div>
            )
          )}
          <Button onClick={handleInspectionResult} className="inspection-result-btn">
            Get Your Car Inspection Result
          </Button>
        </>
      )}
    </div>
  );
};
