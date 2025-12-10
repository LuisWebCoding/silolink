// src/components/AddSiloModal.jsx
import React, { useState, useEffect } from 'react';

function AddSiloModal({ isOpen, onClose, onSave, siloInicial }) {
  const [armazem, setArmazem] = useState('');
  const [data, setData] = useState('');
  const [semente, setSemente] = useState('');
  const [quantidade, setQuantidade] = useState('');
  const [tipoSensor, setTipoSensor] = useState('');
  const [statusSensor, setStatusSensor] = useState('');

  // EFFECT: Monitora quando o modal abre ou quando o 'siloInicial' muda
  useEffect(() => {
    if (isOpen) {
      if (siloInicial) {
        // MODO EDIÇÃO: Preenche os campos com os dados existentes
        setArmazem(siloInicial.armazem);
        // O campo 'data' precisa ser formatado para o input type="date" (YYYY-MM-DD)
        const dataFormatada = siloInicial.data.split('/').reverse().join('-');
        setData(dataFormatada);
        setSemente(siloInicial.semente);
        setQuantidade(siloInicial.quantidade.replace(' Ton', '').replace(',', '.')); 
        
        // Preencher dados de sensor se existirem
        if (siloInicial.backendData && siloInicial.backendData.sensores && siloInicial.backendData.sensores.length > 0) {
          const sensor = siloInicial.backendData.sensores[0];
          setTipoSensor(sensor.tipoSensor || '');
          setStatusSensor(sensor.status || '');
        } else {
          setTipoSensor('');
          setStatusSensor('');
        }
      } else {
        // MODO CRIAÇÃO: Limpa os campos
        setArmazem('');
        setData('');
        setSemente('');
        setQuantidade('');
        setTipoSensor('');
        setStatusSensor('');
      }
    }
  }, [isOpen, siloInicial]);

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    
    // Construir objeto de Lote
    const lotes = [];
    if (data && semente && quantidade) {
      lotes.push({
        tipoSemente: semente,
        quantidade: parseFloat(quantidade),
        dataEntrada: new Date(data).toISOString(), // Converter para ISO string
        dataSaida: null
      });
    }
    
    // Construir objeto de Sensor
    const sensores = [];
    if (tipoSensor && statusSensor) {
      sensores.push({
        tipoSensor: tipoSensor,
        status: statusSensor
      });
    }
    
    const dados = {
      armazem,
      lotes,
      sensores
    };
    
    onSave(dados);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        {/* Título dinâmico */}
        <h2>{siloInicial ? 'Editar Silo' : 'Adicionar Novo Silo'}</h2>
        
        <form onSubmit={handleSubmit}>
          {/* Inputs para LocalArmazenamento */}
          <div className="form-group">
            <label>Armazém</label>
            <input 
              type="text" required value={armazem}
              onChange={(e) => setArmazem(e.target.value)}
            />
          </div>
          
          {/* Inputs para Lote */}
          <div className="form-group">
            <label>Data de Armazenamento</label>
            <input 
              type="date" required value={data}
              onChange={(e) => setData(e.target.value)}
            />
          </div>
          
          <div className="form-group">
            <label>Semente</label>
            <select required value={semente} onChange={(e) => setSemente(e.target.value)}>
              <option value="">Selecione...</option>
              <option value="Soja">Soja</option>
              <option value="Milho">Milho</option>
              <option value="Trigo">Trigo</option>
              <option value="Linhaça">Linhaça</option>
              <option value="Gergelim">Gergelim</option>
              <option value="Semente de abóbora">Semente de abóbora</option>
              <option value="Semente de tomate">Semente de Tomate</option>
            </select>
          </div>
          
          <div className="form-group">
            <label>Quantidade (Ton)</label>
            <input 
              type="number" required value={quantidade}
              onChange={(e) => setQuantidade(e.target.value)}
            />
          </div>

          {/* Inputs para Sensor */}
          <div className="form-group">
            <label>Tipo de Sensor</label>
            <select value={tipoSensor} onChange={(e) => setTipoSensor(e.target.value)}>
              <option value="">Selecione...</option>
              <option value="Temperatura">Temperatura</option>
              <option value="Umidade">Umidade</option>
              <option value="Pressão">Pressão</option>
              <option value="Luminosidade">Luminosidade</option>
              <option value="Múltiplo">Múltiplo</option>
            </select>
          </div>
          
          <div className="form-group">
            <label>Status do Sensor</label>
            <select value={statusSensor} onChange={(e) => setStatusSensor(e.target.value)}>
              <option value="">Selecione...</option>
              <option value="Ativo">Ativo</option>
              <option value="Inativo">Inativo</option>
              <option value="Manutenção">Manutenção</option>
              <option value="Defeito">Defeito</option>
            </select>
          </div>

          <div className="modal-actions">
            <button type="button" className="btn-cancel" onClick={onClose}>
              Cancelar
            </button>
            <button type="submit" className="btn-save">
              {siloInicial ? 'Salvar Alterações' : 'Salvar Silo'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default AddSiloModal;