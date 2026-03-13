// src/pages/RegisterPage.jsx
import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import logo from '../assets/logo_silo_link.png'; // Importando o logo

function RegisterPage() {
  const navigate = useNavigate();


  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    senha: '', 
    cargo: '',
    nivelAcesso: 'user' 
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
    if (error) setError('');
    if (success) setSuccess('');
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    

    if (!formData.nome || !formData.email || !formData.senha || !formData.cargo) {
      setError("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch('http://localhost:8081/api/usuarios', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nome: formData.nome,
          email: formData.email,
          senha: formData.senha,
          cargo: formData.cargo,
          nivelAcesso: formData.nivelAcesso,
        } )
      });

      if (response.ok) {
        setSuccess('Cadastro realizado com sucesso! Você será redirecionado para o login.');
        setTimeout(() => {
          navigate('/login');
        }, 3000);
      } else {
        let errorMessage = 'Erro ao realizar o cadastro. Tente novamente.';
        try {
            const errorData = await response.json();
            errorMessage = errorData.message || errorMessage;
        } catch (e) {
        }
        setError(errorMessage);
      }
    } catch (err) {
      console.error('Erro ao conectar com o backend:', err);
      setError('Erro ao conectar com o servidor. Verifique se o backend está rodando.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page-container"> {/* Reutilizando o estilo do login */}
      <div className="login-box">
        <div className="login-header">
          <img src={logo} alt="SiloLink Logo" className="login-logo" />
          <h2>Cadastre-se no Silo Link</h2>
        </div>

        <form onSubmit={handleSubmit}>
          
          {/* Campo Nome */}
          <div className="form-group">
            <label htmlFor="nome">Nome Completo *</label>
            <input 
              type="text" 
              id="nome"
              name="nome" 
              value={formData.nome}
              onChange={handleChange}
              required
              placeholder="Seu nome"
            />
          </div>

          {/* Campo Email */}
          <div className="form-group">
            <label htmlFor="email">E-mail Corporativo *</label>
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
            <label htmlFor="senha">Senha *</label>
            <input 
              type="password" 
              id="senha"
              name="senha" 
              value={formData.senha}
              onChange={handleChange}
              required
              placeholder="Crie uma senha"
            />
          </div>

          {/* Campo Cargo (Select) */}
          <div className="form-group">
            <label htmlFor="cargo">Cargo *</label>
            <select 
              name="cargo" 
              id="cargo"
              value={formData.cargo}
              onChange={handleChange}
              required
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

          {/* Mensagem de sucesso */}
          {success && (
            <div className="success-message" style={{ color: 'green', marginBottom: '10px' }}>
              {success}
            </div>
          )}

          <button type="submit" className="btn-login" disabled={loading}>
            {loading ? 'Cadastrando...' : 'Cadastrar Novo Usuário'}
          </button>

          <p className="mt-3 text-center">
            Já tem conta? <Link to="/login">Faça Login</Link>
          </p>

        </form>
      </div>
    </div>
  );
}

export default RegisterPage;
