import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import AddSiteButton from '../components/AddSiteButton';
import StatsCard from '../components/StatsCard';
import SilosTable from '../components/SilosTable';
import AddSiloModal from '../components/AddSiloModal';

function DashboardPage() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [silos, setSilos] = useState([]);
  const [loading, setLoading] = useState(true);
  const API_URL = "http://localhost:8081/api/locais";

  // Função de mapeamento do Backend (expandido) para o Frontend (simplificado)
  const mapBackendToFrontend = (backendData) => {
    return backendData.map(silo => {
      // Campos do Lote (assumindo que usamos o primeiro lote)
      const lote = silo.lotes && silo.lotes.length > 0 ? silo.lotes[0] : {};
      // Campos do Sensor (assumindo que usamos o primeiro sensor)
      const sensor = silo.sensores && silo.sensores.length > 0 ? silo.sensores[0] : {};

      return {
        // Campos do Frontend
        id: silo.idLocal,
        armazem: silo.nomeLocal, // Local de Armazenamento
        lote: lote.idLote || 'N/A', // ID do Lote (para referência)
        data: lote.dataEntrada ? new Date(lote.dataEntrada).toLocaleDateString('pt-BR') : 'N/A', // Data de Armazenamento
        semente: lote.tipoSemente || 'N/A', // Tipo de Semente
        quantidade: lote.quantidade ? `${lote.quantidade} Ton` : 'N/A', // Quantidade de Sementes
        status: sensor.status || 'N/A', // Status do Silo
        
        // Campos do Backend (para facilitar a edição/atualização)
        backendData: {
          idLocal: silo.idLocal,
          nomeLocal: silo.nomeLocal,
          tipoLocal: silo.tipoLocal,
          lotes: silo.lotes || [],
          sensores: silo.sensores || [],
          // ... outros dados necessários para o PUT
        }
      };
    });
  };

  // Função para buscar os dados
  const fetchSilos = async () => {
    setLoading(true);
    try {
      const response = await fetch(API_URL);
      if (!response.ok) {
        throw new Error('Falha ao buscar silos');
      }
      const data = await response.json();
      setSilos(mapBackendToFrontend(data));
    } catch (error) {
      console.error("Erro ao carregar silos:", error);
    } finally {
      setLoading(false);
    }
  };

  // Carregar dados ao montar o componente
  useEffect(() => {
    fetchSilos();
  }, []);
  
  // 1. NOVO ESTADO: Guarda o objeto do silo que estamos editando (ou null)
  const [siloParaEditar, setSiloParaEditar] = useState(null);

  // --- FUNÇÕES ---

  // Agora, ao abrir para CRIAR, garantimos que não há edição pendente
  const openModalToCreate = () => {
    setSiloParaEditar(null);
    setIsModalOpen(true);
  };

  // Nova função chamada pelo botão da Tabela
  const openModalToEdit = (silo) => {
    setSiloParaEditar(silo); // Guarda os dados do silo clicado
    setIsModalOpen(true);    // Abre o modal
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSiloParaEditar(null);
  };

  // Função INTELIGENTE de salvar (Cria ou Atualiza)
  const handleSaveSilo = async (dadosDoSilo) => {
    // Mapeamento do Frontend para o Backend
    const backendData = {
      nomeLocal: dadosDoSilo.armazem,
      tipoLocal: null, 
      lotes: dadosDoSilo.lotes || [], // Enviar dados de lote
      sensores: dadosDoSilo.sensores || [], // Enviar dados de sensor
    };

    let url = API_URL;
    let method = 'POST';

    if (siloParaEditar) {
      // --- MODO EDIÇÃO ---
      const idLocal = siloParaEditar.backendData.idLocal;
      url = `${API_URL}/${idLocal}`;
      method = 'PUT';
      backendData.idLocal = idLocal; 
    }

    try {
      const response = await fetch(url, {
        method: method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(backendData),
      });

      if (!response.ok) {
        throw new Error(`Falha ao salvar o silo: ${response.statusText}`);
      }

      // Após salvar, recarrega a lista
      fetchSilos();
      closeModal();
    } catch (error) {
      console.error("Erro ao salvar o silo:", error);
      alert("Erro ao salvar o silo. Verifique o console para detalhes.");
    }
  };

  const handleDeleteSilo = async (siloParaDeletar) => {
    if (window.confirm("Tem certeza que deseja excluir este silo?")) {
      try {
        const idLocal = siloParaDeletar.backendData.idLocal;
        const response = await fetch(`${API_URL}/${idLocal}`, {
          method: 'DELETE',
        });

        if (!response.ok) {
          throw new Error('Falha ao deletar o silo');
        }

        // Após deletar, recarrega a lista
        fetchSilos();
      } catch (error) {
        console.error("Erro ao deletar o silo:", error);
        alert("Erro ao deletar o silo. Verifique o console para detalhes.");
      }
    }
  };

  return (
    <div className="dashboard-container">
      <Header /> 
      
      <div className="main-content">
        <div className="sidebar">
          {/* Botão lateral sempre abre modo CRIAÇÃO */}
          <AddSiteButton onClick={openModalToCreate} />
        </div>
        
        <div className="data-area">
          <div className="cards-layout">
            <StatsCard title="Silos Registrados" value={silos.length} />
          </div>
          
          <h2>Silos Registrados</h2> 
          
          {loading ? (
            <p>Carregando silos...</p>
          ) : (
            /* Passamos a função de editar para a tabela */
            <SilosTable 
              data={silos} 
              onDelete={handleDeleteSilo} 
              onEdit={openModalToEdit} 
            />
          )}
        </div>
      </div>

      {/* O Modal recebe os dados iniciais (se for edição) */}
      <AddSiloModal 
        isOpen={isModalOpen} 
        onClose={closeModal} 
        onSave={handleSaveSilo}
        siloInicial={siloParaEditar} 
      />
    </div>
  );
}

export default DashboardPage;