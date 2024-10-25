import React from 'react';

export const NoteForm = ({ note, onNoteChange, criteriaId, onOutsideClick, isDisabled }) => {
  return (
    <form className={`NoteForm ${isDisabled ? 'dimmed' : ''}`}>
      <label htmlFor={`note-${criteriaId}`}>Note*</label>
      <input
        type="text"
        id={`note-${criteriaId}`}
        value={note}
        onChange={(e) => onNoteChange(e, criteriaId)}
        className="note-input"
        placeholder="Note is required for unsatisfying criteria"
        disabled={isDisabled}
        required
      />
    </form>
  );
};
