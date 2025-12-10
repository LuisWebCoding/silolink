package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.TransporteDTO;
import com.silolink.silolink_api.dto.LoteDTO;
import com.silolink.silolink_api.dto.LocalArmazenamentoDTO;
import com.silolink.silolink_api.model.Transporte;
import com.silolink.silolink_api.model.Lote;
import com.silolink.silolink_api.model.LocalArmazenamento;
import com.silolink.silolink_api.repository.TransporteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transportes")
public class TransporteController {

    private final TransporteRepository transporteRepository;

    public TransporteController(TransporteRepository transporteRepository) {
        this.transporteRepository = transporteRepository;
    }

    @GetMapping
    public List<TransporteDTO> listar() {
        return transporteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransporteDTO> buscarPorId(@PathVariable Integer id) {
        return transporteRepository.findById(id)
                .map(transporte -> ResponseEntity.ok(toDTO(transporte)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransporteDTO> criar(@RequestBody TransporteDTO dto) {
        Transporte transporte = fromDTO(dto);
        Transporte salvo = transporteRepository.save(transporte);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransporteDTO> atualizar(@PathVariable Integer id, @RequestBody TransporteDTO dto) {
        return transporteRepository.findById(id)
                .map(existente -> {
                    existente.setDataInicio(dto.getDataInicio());
                    existente.setDataFim(dto.getDataFim());
                    existente.setOrigem(dto.getOrigem());
                    existente.setDestino(dto.getDestino());

                    // Atualiza vínculo com Lote
                    if (dto.getLote() != null) {
                        Lote lote = existente.getLote();
                        if (lote == null) lote = new Lote();
                        lote.setIdLote(dto.getLote().getIdLote());
                        existente.setLote(lote);
                    }

                    // Atualiza vínculo com Local (Veículo/Localização)
                    if (dto.getLocalVeiculo() != null) {
                        LocalArmazenamento local = existente.getLocalVeiculo();
                        if (local == null) local = new LocalArmazenamento();
                        local.setIdLocal(dto.getLocalVeiculo().getIdLocal());
                        existente.setLocalVeiculo(local);
                    }

                    Transporte atualizado = transporteRepository.save(existente);
                    return ResponseEntity.ok(toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!transporteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transporteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Métodos Auxiliares de Conversão ---

    private TransporteDTO toDTO(Transporte transporte) {
        TransporteDTO dto = new TransporteDTO();
        dto.setIdTransporte(transporte.getIdTransporte());
        dto.setDataInicio(transporte.getDataInicio());
        dto.setDataFim(transporte.getDataFim());
        dto.setOrigem(transporte.getOrigem());
        dto.setDestino(transporte.getDestino());

        if (transporte.getLote() != null) {
            LoteDTO loteDto = new LoteDTO();
            loteDto.setIdLote(transporte.getLote().getIdLote());
            loteDto.setTipoSemente(transporte.getLote().getTipoSemente());
            // Outros campos do lote se necessário...
            dto.setLote(loteDto);
        }

        if (transporte.getLocalVeiculo() != null) {
            LocalArmazenamentoDTO localDto = new LocalArmazenamentoDTO();
            localDto.setIdLocal(transporte.getLocalVeiculo().getIdLocal());
            localDto.setNomeLocal(transporte.getLocalVeiculo().getNomeLocal());
            dto.setLocalVeiculo(localDto);
        }

        return dto;
    }

    private Transporte fromDTO(TransporteDTO dto) {
        Transporte transporte = new Transporte();
        transporte.setDataInicio(dto.getDataInicio());
        transporte.setDataFim(dto.getDataFim());
        transporte.setOrigem(dto.getOrigem());
        transporte.setDestino(dto.getDestino());

        if (dto.getLote() != null) {
            Lote lote = new Lote();
            lote.setIdLote(dto.getLote().getIdLote());
            transporte.setLote(lote);
        }

        if (dto.getLocalVeiculo() != null) {
            LocalArmazenamento local = new LocalArmazenamento();
            local.setIdLocal(dto.getLocalVeiculo().getIdLocal());
            transporte.setLocalVeiculo(local);
        }

        return transporte;
    }
}