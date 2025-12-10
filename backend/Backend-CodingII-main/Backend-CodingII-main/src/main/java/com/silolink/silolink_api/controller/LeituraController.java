package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.LeituraDTO;
import com.silolink.silolink_api.dto.SensorDTO;
import com.silolink.silolink_api.dto.LoteDTO;
import com.silolink.silolink_api.dto.LocalArmazenamentoDTO;
import com.silolink.silolink_api.model.*;
import com.silolink.silolink_api.repository.LeituraRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leituras")
public class LeituraController {

    private final LeituraRepository leituraRepository;

    public LeituraController(LeituraRepository leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    @GetMapping
    public List<LeituraDTO> listar() {
        return leituraRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraDTO> buscarPorId(@PathVariable Integer id) {
        return leituraRepository.findById(id).map(l -> ResponseEntity.ok(toDTO(l))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LeituraDTO> criar(@RequestBody LeituraDTO dto) {
        Leitura leitura = fromDTO(dto);
        Leitura salvo = leituraRepository.save(leitura);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeituraDTO> atualizar(@PathVariable Integer id, @RequestBody LeituraDTO dto) {
        return leituraRepository.findById(id).map(existente -> {
            existente.setValorTemperatura(dto.getValorTemperatura());
            existente.setValorUmidade(dto.getValorUmidade());
            existente.setValorLuminosidade(dto.getValorLuminosidade());
            existente.setDataHora(dto.getDataHora());

            if (dto.getSensor() != null) {
                Sensor s = existente.getSensor();
                if(s == null) s = new Sensor();
                s.setIdSensor(dto.getSensor().getIdSensor());
                s.setTipoSensor(dto.getSensor().getTipoSensor());
                existente.setSensor(s);
            }
            if (dto.getLote() != null) {
                Lote l = existente.getLote();
                if(l == null) l = new Lote();
                l.setIdLote(dto.getLote().getIdLote());
                l.setTipoSemente(dto.getLote().getTipoSemente());
                existente.setLote(l);
            }
            if (dto.getLocalArmazenamento() != null) {
                LocalArmazenamento loc = existente.getLocalArmazenamento();
                if(loc == null) loc = new LocalArmazenamento();
                loc.setIdLocal(dto.getLocalArmazenamento().getIdLocal());
                loc.setNomeLocal(dto.getLocalArmazenamento().getNomeLocal());
                existente.setLocalArmazenamento(loc);
            }
            return ResponseEntity.ok(toDTO(leituraRepository.save(existente)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!leituraRepository.existsById(id)) return ResponseEntity.notFound().build();
        leituraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LeituraDTO toDTO(Leitura entity) {
        LeituraDTO dto = new LeituraDTO();
        dto.setIdLeitura(entity.getIdLeitura());
        dto.setValorTemperatura(entity.getValorTemperatura());
        dto.setValorUmidade(entity.getValorUmidade());
        dto.setValorLuminosidade(entity.getValorLuminosidade());
        dto.setDataHora(entity.getDataHora());

        if (entity.getSensor() != null) {
            SensorDTO s = new SensorDTO();
            s.setIdSensor(entity.getSensor().getIdSensor());
            s.setTipoSensor(entity.getSensor().getTipoSensor());
            dto.setSensor(s);
        }
        if (entity.getLote() != null) {
            LoteDTO l = new LoteDTO();
            l.setIdLote(entity.getLote().getIdLote());
            l.setTipoSemente(entity.getLote().getTipoSemente());
            dto.setLote(l);
        }
        if (entity.getLocalArmazenamento() != null) {
            LocalArmazenamentoDTO loc = new LocalArmazenamentoDTO();
            loc.setIdLocal(entity.getLocalArmazenamento().getIdLocal());
            loc.setNomeLocal(entity.getLocalArmazenamento().getNomeLocal());
            dto.setLocalArmazenamento(loc);
        }
        return dto;
    }

    private Leitura fromDTO(LeituraDTO dto) {
        Leitura entity = new Leitura();
        entity.setValorTemperatura(dto.getValorTemperatura());
        entity.setValorUmidade(dto.getValorUmidade());
        entity.setValorLuminosidade(dto.getValorLuminosidade());
        entity.setDataHora(dto.getDataHora());

        if (dto.getSensor() != null) {
            Sensor s = new Sensor();
            s.setIdSensor(dto.getSensor().getIdSensor());
            entity.setSensor(s);
        }
        if (dto.getLote() != null) {
            Lote l = new Lote();
            l.setIdLote(dto.getLote().getIdLote());
            entity.setLote(l);
        }
        if (dto.getLocalArmazenamento() != null) {
            LocalArmazenamento loc = new LocalArmazenamento();
            loc.setIdLocal(dto.getLocalArmazenamento().getIdLocal());
            entity.setLocalArmazenamento(loc);
        }
        return entity;
    }
}