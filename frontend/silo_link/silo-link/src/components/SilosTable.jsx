import React from 'react';

// Recebemos onEdit aqui
const TableRow = ({ silo, onDelete, onEdit }) => (
  <tr>
    <td>{silo.id}</td>
    <td>{silo.armazem}</td>
    <td>{silo.lote}</td>
    <td>{silo.data}</td>
    <td>{silo.semente}</td>
    <td>{silo.status}</td>
    <td>{silo.quantidade}</td>
    <td>
      <div className="table-actions">
        {/* 1. Ao clicar, chama onEdit passando o objeto silo inteiro */}
        <button 
          className="edit-button" 
          onClick={() => onEdit(silo)}
        >
          Editar Silo
        </button>
        
        <button 
          className="delete-button" 
          onClick={() => onDelete(silo)}
          title="Excluir Silo"
        >
          🗑️
        </button>
      </div>
    </td>
  </tr>
);

// Recebemos onEdit aqui também
function SilosTable({ data, onDelete, onEdit }) {
  const headers = [
    'Id', 'Armazém', 'Lote', 'Data de Armazenamento', 
    'Semente', 'Status do Silo', 'Quantidade de Sementes', 'Ações'
  ];

  return (
    <div className="table-container">
      {data.length === 0 ? (
        <p style={{ textAlign: 'center', padding: '20px', color: '#666' }}>
          Nenhum silo registrado.
        </p>
      ) : (
        <table>
          <thead>
            <tr>
              {headers.map(header => (
                <th key={header}>{header}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data.map(silo => (
              <TableRow 
                key={silo.id} 
                silo={silo} 
                onDelete={onDelete}
                onEdit={onEdit} // Repassa a função
              />
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default SilosTable;  