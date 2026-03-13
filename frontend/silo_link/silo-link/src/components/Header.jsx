// src/components/Header.jsx
import React, { useState, useEffect } from 'react';
import logo from '../assets/logo_silo_link.png';

function Header() {
  const [nomeUsuario, setNomeUsuario] = useState('');

  
  useEffect(() => {
    const usuarioLogado = localStorage.getItem('nomeUsuario');
    if (usuarioLogado) {
      setNomeUsuario(usuarioLogado);
    } else {
      buscarDadosUsuario();
    }
  }, []);

  
  const buscarDadosUsuario = async () => {
    try {
      const response = await fetch('http://localhost:8081/api/usuarios/perfil', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        }
      });

      if (response.ok) {
        const data = await response.json();
        setNomeUsuario(data.nome);
        localStorage.setItem('nomeUsuario', data.nome);
      }
    } catch (error) {
      console.error('Erro ao buscar dados do usuário:', error);
      setNomeUsuario('Usuário');
    }
  };

  // Função para fazer logout
  const handleLogout = () => {
    localStorage.removeItem('nomeUsuario');
    localStorage.removeItem('token');
    window.location.href = '/login';
  };

  return (
    <header className="silo-header">
      <div className="header-container">
        {/* Seção do Logo e Título */}
        <div className="logo-section">
          <img src={logo} alt="SiloLink Logo" className="logo-image" />
          <span className="logo-text">SiloLink</span>
        </div>

        {/* Seção do Usuário (lado direito) */}
        <div className="user-section">
          <div className="user-info">
            <span className="user-name">{nomeUsuario || 'Usuário'}</span>
            <button className="logout-btn" onClick={handleLogout} title="Fazer logout">
              🚪
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;
