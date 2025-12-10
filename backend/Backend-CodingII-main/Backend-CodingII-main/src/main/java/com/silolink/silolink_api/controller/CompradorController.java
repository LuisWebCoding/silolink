package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.model.Comprador;
import com.silolink.silolink_api.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compradores")
public class CompradorController {

    @Autowired
    private CompradorRepository compradorRepository;

    @GetMapping
    public List<Comprador> listarTodos() {
        return compradorRepository.findAll();
    }

    @PostMapping
    public Comprador criar(@RequestBody Comprador comprador) {
        return compradorRepository.save(comprador);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comprador> buscarPorId(@PathVariable Integer id) {
        return compradorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comprador> atualizar(@PathVariable Integer id, @RequestBody Comprador compradorDetails) {
        return compradorRepository.findById(id)
                .map(comprador -> {
                    comprador.setNome(compradorDetails.getNome());
                    comprador.setDocumento(compradorDetails.getDocumento());
                    comprador.setContato(compradorDetails.getContato());
                    Comprador atualizado = compradorRepository.save(comprador);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Integer id) {
        return compradorRepository.findById(id)
                .map(comprador -> {
                    compradorRepository.delete(comprador);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}