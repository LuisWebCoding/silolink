import React from 'react';

function StatsCard({ title, value }) {
  return (
    <div className="stats-card">
      <h3 className="card-title">{title}</h3>
      <p className="card-value">{value}</p>
    </div>
  );
}

export default StatsCard;