// src/components/Header.jsx
import React, { useState, useEffect } from 'react';
import logo from '../assets/logo_silo_link.png';

function Header() {
  const [nomeUsuario, setNomeUsuario] = useState('');

  // Buscar o nome do usuário do localStorage ou da API
  useEffect(() => {
    // Opção 1: Buscar do localStorage (se você armazena lá após login)
    const usuarioLogado = localStorage.getItem('nomeUsuario');
    if (usuarioLogado) {
      setNomeUsuario(usuarioLogado);
    } else {
      // Opção 2: Buscar da API (se você tiver um endpoint que retorna dados do usuário)
      buscarDadosUsuario();
    }
  }, []);

  // Função para buscar dados do usuário da API
  const buscarDadosUsuario = async () => {
    try {
      // Ajuste a URL conforme seu backend
      const response = await fetch('http://localhost:8081/api/usuarios/perfil', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          // Se usar autenticação, adicione o token aqui
          // 'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        setNomeUsuario(data.nome);
        // Opcionalmente, armazene no localStorage para próximas vezes
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
    localStorage.removeItem('token'); // Se usar token
    // Redirecionar para página de login
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