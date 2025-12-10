package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.model.Alerta;
import com.silolink.silolink_api.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaRepository alertaRepository;

    @GetMapping
    public List<Alerta> listarTodos() {
        return alertaRepository.findAll();
    }

    @PostMapping
    public Alerta criar(@RequestBody Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Integer id) {
        return alertaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> atualizar(@PathVariable Integer id, @RequestBody Alerta alertaDetails) {
        return alertaRepository.findById(id)
                .map(alerta -> {
                    alerta.setTipoAlerta(alertaDetails.getTipoAlerta());
                    alerta.setValorLido(alertaDetails.getValorLido());
                    alerta.setDescricao(alertaDetails.getDescricao());
                    alerta.setNivelRisco(alertaDetails.getNivelRisco());
                    alerta.setDataHora(alertaDetails.getDataHora());
                    alerta.setSensor(alertaDetails.getSensor());
                    alerta.setLote(alertaDetails.getLote());
                    alerta.setLocalArmazenamento(alertaDetails.getLocalArmazenamento());
                    alerta.setUsuarioVisualizou(alertaDetails.getUsuarioVisualizou());

                    Alerta atualizado = alertaRepository.save(alerta);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Integer id) {
        return alertaRepository.findById(id)
                .map(alerta -> {
                    alertaRepository.delete(alerta);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}