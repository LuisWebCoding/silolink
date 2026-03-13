// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom'; // <-- Adicionado 'Link'
import logo from '../assets/logo_silo_link.png'; // Importando o logo

function LoginPage() {
  const navigate = useNavigate();

 
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    password: '',
    cargo: ''
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');


  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
    if (error) setError('');
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    

    if (!formData.email || !formData.password) {
      setError("Por favor, preencha email e senha.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch('http://localhost:8081/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: formData.email,
          senha: formData.password
        } )
      });

      const data = await response.json();

      if (response.ok && data.sucesso) {
        alert(`Bem-vindo(a)! ${data.mensagem}`);
        
        const nomeUsuario = data.nome || formData.nome || formData.email;
        localStorage.setItem('nomeUsuario', nomeUsuario);

        
        localStorage.setItem('emailUsuario', formData.email);
        if (formData.cargo) {
          localStorage.setItem('cargoUsuario', formData.cargo);
        }

        navigate('/dashboard');
      } else {
        setError(data.mensagem || 'Erro ao fazer login. Verifique suas credenciais.');
      }
    } catch (err) {
      console.error('Erro ao conectar com o backend:', err);
      setError('Erro ao conectar com o servidor. Verifique se o backend está rodando.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page-container">
      <div className="login-box">
        <div className="login-header">
          <img src={logo} alt="SiloLink Logo" className="login-logo" />
          <h2>Silo Link</h2>
        </div>

        <form onSubmit={handleSubmit}>
          
          {/* Campo Nome */}
          <div className="form-group">
            <label htmlFor="nome">Nome Completo</label>
            <input 
              type="text" 
              id="nome"
              name="nome" 
              value={formData.nome}
              onChange={handleChange}
              placeholder="Seu nome"
            />
          </div>

          {/* Campo Email */}
          <div className="form-group">
            <label htmlFor="email">E-mail Corporativo</label>
            <input 
              type="email" 
              id="email"
              name="email" 
              value={formData.email}
              onChange={handleChange}
              required
              placeholder="exemplo@silolink.com"
            />
          </div>

          {/* Campo Senha */}
          <div className="form-group">
            <label htmlFor="password">Senha</label>
            <input 
              type="password" 
              id="password"
              name="password" 
              value={formData.password}
              onChange={handleChange}
              required
              placeholder="******"
            />
          </div>

          {/* Campo Cargo (Select) */}
          <div className="form-group">
            <label htmlFor="cargo">Cargo</label>
            <select 
              name="cargo" 
              id="cargo"
              value={formData.cargo}
              onChange={handleChange}
            >
              <option value="">Selecione seu cargo...</option>
              <option value="Administrador">Administrador</option>
              <option value="Gerente de Operações">Gerente de Operações</option>
              <option value="Operador de Silo">Operador de Silo</option>
              <option value="Analista de Qualidade">Analista de Qualidade</option>
            </select>
          </div>

          {/* Mensagem de erro */}
          {error && (
            <div className="error-message" style={{ color: 'red', marginBottom: '10px' }}>
              {error}
            </div>
          )}

          <button type="submit" className="btn-login" disabled={loading}>
            {loading ? 'Entrando...' : 'Entrar no Sistema'}
          </button>

          {/* NOVO LINK PARA CADASTRO */}
          <p className="mt-3 text-center">
            Não tem conta? <Link to="/register">Cadastre-se</Link>
          </p>

        </form>
      </div>
    </div>
  );
}

export default LoginPage;
