import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare, faFloppyDisk } from '@fortawesome/free-solid-svg-icons';

export const InspectionCriteria = ({ criteria, editCriteria, toggleComplete, confirmCriteria, isConfirmDisabled }) => {
  return (
    <div className={`InspectionCriteria ${criteria.confirmed ? "dimmed" : ""}`}>
      <p
        className={`${criteria.completed ? "completed" : "incompleted"}`}
        onClick={() => toggleComplete(criteria.id)}
      >
        {criteria.criteria}
      </p>
      <div className="actions">
        <FontAwesomeIcon className="edit-icon" icon={faPenToSquare} onClick={() => editCriteria(criteria.id)} />

        <FontAwesomeIcon 
          className={`confirm-icon ${isConfirmDisabled ? 'disabled' : ''}`} 
          icon={faFloppyDisk} 
          onClick={() => !isConfirmDisabled && confirmCriteria(criteria.id)} 
          style={{ cursor: isConfirmDisabled ? 'not-allowed' : 'pointer' }}
        />
      </div>
    </div>
  );
};
