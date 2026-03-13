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


  const mapBackendToFrontend = (backendData) => {
    return backendData.map(silo => {
      const lote = silo.lotes && silo.lotes.length > 0 ? silo.lotes[0] : {};
      const sensor = silo.sensores && silo.sensores.length > 0 ? silo.sensores[0] : {};

      return {
        id: silo.idLocal,
        armazem: silo.nomeLocal,
        lote: lote.idLote || 'N/A', 
        data: lote.dataEntrada ? new Date(lote.dataEntrada).toLocaleDateString('pt-BR') : 'N/A', 
        semente: lote.tipoSemente || 'N/A', 
        quantidade: lote.quantidade ? `${lote.quantidade} Ton` : 'N/A', 
        status: sensor.status || 'N/A', 
        
        backendData: {
          idLocal: silo.idLocal,
          nomeLocal: silo.nomeLocal,
          tipoLocal: silo.tipoLocal,
          lotes: silo.lotes || [],
          sensores: silo.sensores || [],
        }
      };
    });
  };


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
  
  useEffect(() => {
    fetchSilos();
  }, []);
  
  
  const [siloParaEditar, setSiloParaEditar] = useState(null);

  // --- FUNÇÕES ---

  const openModalToCreate = () => {
    setSiloParaEditar(null);
    setIsModalOpen(true);
  };

  const openModalToEdit = (silo) => {
    setSiloParaEditar(silo);
    setIsModalOpen(true);  
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSiloParaEditar(null);
  };

  
  const handleSaveSilo = async (dadosDoSilo) => {
    
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
