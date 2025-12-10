package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.EmpresaDTO;
import com.silolink.silolink_api.model.Empresa;
import com.silolink.silolink_api.repository.EmpresaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @GetMapping
    public List<EmpresaDTO> listar() {
        return empresaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> buscarPorId(@PathVariable Integer id) {
        return empresaRepository.findById(id)
                .map(empresa -> ResponseEntity.ok(toDTO(empresa)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> criar(@RequestBody EmpresaDTO dto) {
        Empresa empresa = fromDTO(dto);
        Empresa salvo = empresaRepository.save(empresa);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> atualizar(@PathVariable Integer id, @RequestBody EmpresaDTO dto) {
        return empresaRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    existente.setTipo(dto.getTipo());

                    Empresa atualizado = empresaRepository.save(existente);
                    return ResponseEntity.ok(toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!empresaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Conversões
    private EmpresaDTO toDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setIdEmpresa(empresa.getIdEmpresa());
        dto.setNome(empresa.getNome());
        dto.setTipo(empresa.getTipo());
        return dto;
    }

    private Empresa fromDTO(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setNome(dto.getNome());
        empresa.setTipo(dto.getTipo());
        return empresa;
    }
}