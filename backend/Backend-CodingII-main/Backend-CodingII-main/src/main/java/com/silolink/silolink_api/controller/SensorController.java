package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.SensorDTO;
import com.silolink.silolink_api.dto.LocalArmazenamentoDTO;
import com.silolink.silolink_api.dto.EmpresaDTO;
import com.silolink.silolink_api.model.Sensor;
import com.silolink.silolink_api.model.LocalArmazenamento;
import com.silolink.silolink_api.repository.SensorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping
    public List<SensorDTO> listar() {
        return sensorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> buscarPorId(@PathVariable Integer id) {
        return sensorRepository.findById(id)
                .map(sensor -> ResponseEntity.ok(toDTO(sensor)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SensorDTO> criar(@RequestBody SensorDTO dto) {
        Sensor sensor = fromDTO(dto);
        Sensor salvo = sensorRepository.save(sensor);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> atualizar(@PathVariable Integer id, @RequestBody SensorDTO dto) {
        return sensorRepository.findById(id)
                .map(existente -> {
                    existente.setTipoSensor(dto.getTipoSensor());
                    existente.setStatus(dto.getStatus());

                    // Atualiza o vínculo com o Local de Armazenamento se fornecido
                    if (dto.getLocalArmazenamento() != null) {
                        LocalArmazenamento local = existente.getLocalArmazenamento();
                        if (local == null) {
                            local = new LocalArmazenamento();
                        }
                        local.setIdLocal(dto.getLocalArmazenamento().getIdLocal());

                        // Atualiza apenas se os campos vierem preenchidos no DTO para evitar sobrescrever com null
                        if (dto.getLocalArmazenamento().getNomeLocal() != null) {
                            local.setNomeLocal(dto.getLocalArmazenamento().getNomeLocal());
                        }
                        existente.setLocalArmazenamento(local);
                    }

                    Sensor atualizado = sensorRepository.save(existente);
                    return ResponseEntity.ok(toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!sensorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sensorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Métodos Auxiliares de Conversão ---

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setIdSensor(sensor.getIdSensor());
        dto.setTipoSensor(sensor.getTipoSensor());
        dto.setStatus(sensor.getStatus());

        if (sensor.getLocalArmazenamento() != null) {
            LocalArmazenamentoDTO localDto = new LocalArmazenamentoDTO();
            localDto.setIdLocal(sensor.getLocalArmazenamento().getIdLocal());
            localDto.setNomeLocal(sensor.getLocalArmazenamento().getNomeLocal());
            localDto.setTipoLocal(sensor.getLocalArmazenamento().getTipoLocal());

            // Se quiser exibir a empresa do local também (nested DTO profundo)
            if (sensor.getLocalArmazenamento().getEmpresa() != null) {
                EmpresaDTO empresaDto = new EmpresaDTO();
                empresaDto.setIdEmpresa(sensor.getLocalArmazenamento().getEmpresa().getIdEmpresa());
                empresaDto.setNome(sensor.getLocalArmazenamento().getEmpresa().getNome());
                localDto.setEmpresa(empresaDto);
            }

            dto.setLocalArmazenamento(localDto);
        }

        return dto;
    }

    private Sensor fromDTO(SensorDTO dto) {
        Sensor sensor = new Sensor();
        sensor.setTipoSensor(dto.getTipoSensor());
        sensor.setStatus(dto.getStatus());

        if (dto.getLocalArmazenamento() != null) {
            LocalArmazenamento local = new LocalArmazenamento();
            local.setIdLocal(dto.getLocalArmazenamento().getIdLocal());
            // Para criar/vincular, geralmente só o ID é obrigatório aqui
            sensor.setLocalArmazenamento(local);
        }

        return sensor;
    }
}