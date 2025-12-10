// src/App.jsx
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

// Importando as páginas
import DashboardPage from './pages/DashboardPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage'; // <-- NOVO IMPORT

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Rota raiz: Redireciona automaticamente para o login */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        
        {/* Rota da tela de Login */}
        <Route path="/login" element={<LoginPage />} />
        
        {/* Rota da tela de Cadastro */}
        <Route path="/register" element={<RegisterPage />} /> {/* <-- NOVA ROTA */}
        
        {/* Rota do Dashboard */}
        <Route path="/dashboard" element={<DashboardPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
