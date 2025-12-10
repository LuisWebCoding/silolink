// src/components/AddSiteButton.jsx
import React from 'react';

function AddSiteButton({ onClick }) {
  return (
    <button className="add-site-button" onClick={onClick}>
      <span className="icon">+</span>
      <span className="text">Adicionar Silo</span>
    </button>
  );
}

export default AddSiteButton;