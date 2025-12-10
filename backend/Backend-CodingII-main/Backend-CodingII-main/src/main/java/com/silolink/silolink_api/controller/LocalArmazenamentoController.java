package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.LocalArmazenamentoDTO;
import com.silolink.silolink_api.dto.EmpresaDTO;
import com.silolink.silolink_api.dto.LoteDTO;
import com.silolink.silolink_api.dto.SensorDTO;
import com.silolink.silolink_api.model.LocalArmazenamento;
import com.silolink.silolink_api.model.Empresa;
import com.silolink.silolink_api.model.Lote;
import com.silolink.silolink_api.model.Sensor;
import com.silolink.silolink_api.repository.LocalArmazenamentoRepository;
import com.silolink.silolink_api.repository.LoteRepository;
import com.silolink.silolink_api.repository.SensorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/locais")
@CrossOrigin(origins = "http://localhost:5173")
public class LocalArmazenamentoController {

    private final LocalArmazenamentoRepository repository;
    private final LoteRepository loteRepository;
    private final SensorRepository sensorRepository;

    public LocalArmazenamentoController(LocalArmazenamentoRepository repository, 
                                       LoteRepository loteRepository,
                                       SensorRepository sensorRepository) {
        this.repository = repository;
        this.loteRepository = loteRepository;
        this.sensorRepository = sensorRepository;
    }

    @GetMapping
    public List<LocalArmazenamentoDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalArmazenamentoDTO> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(l -> ResponseEntity.ok(toDTO(l)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LocalArmazenamentoDTO> criar(@RequestBody LocalArmazenamentoDTO dto) {
        LocalArmazenamento entity = fromDTO(dto);
        LocalArmazenamento salvo = repository.save(entity);
        
        // Processar Lotes
        if (dto.getLotes() != null && !dto.getLotes().isEmpty()) {
            for (LoteDTO loteDTO : dto.getLotes()) {
                Lote lote = new Lote();
                lote.setTipoSemente(loteDTO.getTipoSemente());
                lote.setQuantidade(loteDTO.getQuantidade());
                lote.setDataEntrada(loteDTO.getDataEntrada());
                lote.setDataSaida(loteDTO.getDataSaida());
                lote.setLocalArmazenamento(salvo);
                loteRepository.save(lote);
            }
        }
        
        // Processar Sensores
        if (dto.getSensores() != null && !dto.getSensores().isEmpty()) {
            for (SensorDTO sensorDTO : dto.getSensores()) {
                Sensor sensor = new Sensor();
                sensor.setTipoSensor(sensorDTO.getTipoSensor());
                sensor.setStatus(sensorDTO.getStatus());
                sensor.setLocalArmazenamento(salvo);
                sensorRepository.save(sensor);
            }
        }
        
        // Recarregar para retornar dados completos
        salvo = repository.findById(salvo.getIdLocal()).orElse(salvo);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalArmazenamentoDTO> atualizar(@PathVariable Integer id, @RequestBody LocalArmazenamentoDTO dto) {
        return repository.findById(id)
                .map(existente -> {
                    existente.setNomeLocal(dto.getNomeLocal());
                    existente.setTipoLocal(dto.getTipoLocal());

                    if (dto.getEmpresa() != null) {
                        Empresa empresa = existente.getEmpresa();
                        if (empresa == null) empresa = new Empresa();
                        empresa.setIdEmpresa(dto.getEmpresa().getIdEmpresa());
                        empresa.setNome(dto.getEmpresa().getNome());
                        empresa.setTipo(dto.getEmpresa().getTipo());
                        existente.setEmpresa(empresa);
                    }
                    
                    // ✅ CORREÇÃO PARA LOTES - Usar clear() em vez de deleteAll()
                    if (dto.getLotes() != null) {
                        // Limpar a coleção existente (não deletar do banco)
                        if (existente.getLotes() != null) {
                            existente.getLotes().clear();
                        } else {
                            existente.setLotes(new ArrayList<>());
                        }
                        
                        // Adicionar novos lotes diretamente à coleção
                        for (LoteDTO loteDTO : dto.getLotes()) {
                            Lote lote = new Lote();
                            lote.setTipoSemente(loteDTO.getTipoSemente());
                            lote.setQuantidade(loteDTO.getQuantidade());
                            lote.setDataEntrada(loteDTO.getDataEntrada());
                            lote.setDataSaida(loteDTO.getDataSaida());
                            lote.setLocalArmazenamento(existente);
                            existente.getLotes().add(lote);
                        }
                    }
                    
                    // ✅ CORREÇÃO PARA SENSORES - Usar clear() em vez de deleteAll()
                    if (dto.getSensores() != null) {
                        // Limpar a coleção existente (não deletar do banco)
                        if (existente.getSensores() != null) {
                            existente.getSensores().clear();
                        } else {
                            existente.setSensores(new ArrayList<>());
                        }
                        
                        // Adicionar novos sensores diretamente à coleção
                        for (SensorDTO sensorDTO : dto.getSensores()) {
                            Sensor sensor = new Sensor();
                            sensor.setTipoSensor(sensorDTO.getTipoSensor());
                            sensor.setStatus(sensorDTO.getStatus());
                            sensor.setLocalArmazenamento(existente);
                            existente.getSensores().add(sensor);
                        }
                    }
                    
                    // Salvar tudo de uma vez
                    LocalArmazenamento atualizado = repository.save(existente);
                    return ResponseEntity.ok(toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LocalArmazenamentoDTO toDTO(LocalArmazenamento local) {
        LocalArmazenamentoDTO dto = new LocalArmazenamentoDTO();
        dto.setIdLocal(local.getIdLocal());
        dto.setNomeLocal(local.getNomeLocal());
        dto.setTipoLocal(local.getTipoLocal());
        
        if (local.getEmpresa() != null) {
            EmpresaDTO empDto = new EmpresaDTO();
            empDto.setIdEmpresa(local.getEmpresa().getIdEmpresa());
            empDto.setNome(local.getEmpresa().getNome());
            empDto.setTipo(local.getEmpresa().getTipo());
            dto.setEmpresa(empDto);
        }
        
        // Converter Lotes
        if (local.getLotes() != null) {
            List<LoteDTO> lotesDTO = local.getLotes().stream().map(lote -> {
                LoteDTO loteDTO = new LoteDTO();
                loteDTO.setIdLote(lote.getIdLote());
                loteDTO.setTipoSemente(lote.getTipoSemente());
                loteDTO.setQuantidade(lote.getQuantidade());
                loteDTO.setDataEntrada(lote.getDataEntrada());
                loteDTO.setDataSaida(lote.getDataSaida());
                return loteDTO;
            }).collect(Collectors.toList());
            dto.setLotes(lotesDTO);
        }
        
        // Converter Sensores
        if (local.getSensores() != null) {
            List<SensorDTO> sensoresDTO = local.getSensores().stream().map(sensor -> {
                SensorDTO sensorDTO = new SensorDTO();
                sensorDTO.setIdSensor(sensor.getIdSensor());
                sensorDTO.setTipoSensor(sensor.getTipoSensor());
                sensorDTO.setStatus(sensor.getStatus());
                return sensorDTO;
            }).collect(Collectors.toList());
            dto.setSensores(sensoresDTO);
        }
        
        return dto;
    }

    private LocalArmazenamento fromDTO(LocalArmazenamentoDTO dto) {
        LocalArmazenamento local = new LocalArmazenamento();
        // Não setamos o ID aqui, pois ele é gerado pelo banco (ou já existe no PUT)
        // local.setIdLocal(dto.getIdLocal()); // Não necessário no fromDTO para POST/PUT
        local.setNomeLocal(dto.getNomeLocal());
        local.setTipoLocal(dto.getTipoLocal());
        
        if (dto.getEmpresa() != null) {
            Empresa emp = new Empresa();
            // É crucial que o ID da Empresa seja setado se for uma relação ManyToOne
            // O DTO pode vir sem o ID da Empresa, causando erro se o banco exigir
            if (dto.getEmpresa().getIdEmpresa() != null) {
                emp.setIdEmpresa(dto.getEmpresa().getIdEmpresa());
            }
            // Os outros campos (nome, tipo) não são estritamente necessários para a relação
            // mas mantemos para consistência, se existirem no DTO
            emp.setNome(dto.getEmpresa().getNome());
            emp.setTipo(dto.getEmpresa().getTipo());
            local.setEmpresa(emp);
        }
        return local;
    }
}