package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.LoteDTO;
import com.silolink.silolink_api.dto.EmpresaDTO;
import com.silolink.silolink_api.dto.CompradorDTO;
import com.silolink.silolink_api.model.Lote;
import com.silolink.silolink_api.model.Empresa;
import com.silolink.silolink_api.model.Comprador;
import com.silolink.silolink_api.repository.LoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    private final LoteRepository loteRepository;

    public LoteController(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @GetMapping
    public List<LoteDTO> listar() {
        return loteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteDTO> buscarPorId(@PathVariable Integer id) {
        return loteRepository.findById(id).map(l -> ResponseEntity.ok(toDTO(l))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LoteDTO> criar(@RequestBody LoteDTO dto) {
        Lote lote = fromDTO(dto);
        Lote salvo = loteRepository.save(lote);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteDTO> atualizar(@PathVariable Integer id, @RequestBody LoteDTO dto) {
        return loteRepository.findById(id).map(existente -> {
            existente.setTipoSemente(dto.getTipoSemente());
            existente.setQuantidade(dto.getQuantidade());
            existente.setDataEntrada(dto.getDataEntrada());
            existente.setDataSaida(dto.getDataSaida());

            if (dto.getEmpresaDona() != null) {
                Empresa emp = existente.getEmpresaDona();
                if (emp == null) emp = new Empresa();
                emp.setIdEmpresa(dto.getEmpresaDona().getIdEmpresa());
                emp.setNome(dto.getEmpresaDona().getNome());
                emp.setTipo(dto.getEmpresaDona().getTipo());
                existente.setEmpresaDona(emp);
            }

            if (dto.getComprador() != null) {
                Comprador comp = existente.getComprador();
                if (comp == null) comp = new Comprador();
                comp.setIdComprador(dto.getComprador().getIdComprador());
                comp.setNome(dto.getComprador().getNome());
                comp.setDocumento(dto.getComprador().getDocumento());
                comp.setContato(dto.getComprador().getContato());
                existente.setComprador(comp);
            }
            return ResponseEntity.ok(toDTO(loteRepository.save(existente)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!loteRepository.existsById(id)) return ResponseEntity.notFound().build();
        loteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LoteDTO toDTO(Lote lote) {
        LoteDTO dto = new LoteDTO();
        dto.setIdLote(lote.getIdLote());
        dto.setTipoSemente(lote.getTipoSemente());
        dto.setQuantidade(lote.getQuantidade());
        dto.setDataEntrada(lote.getDataEntrada());
        dto.setDataSaida(lote.getDataSaida());

        if (lote.getEmpresaDona() != null) {
            EmpresaDTO emp = new EmpresaDTO();
            emp.setIdEmpresa(lote.getEmpresaDona().getIdEmpresa());
            emp.setNome(lote.getEmpresaDona().getNome());
            emp.setTipo(lote.getEmpresaDona().getTipo());
            dto.setEmpresaDona(emp);
        }
        if (lote.getComprador() != null) {
            CompradorDTO comp = new CompradorDTO();
            comp.setIdComprador(lote.getComprador().getIdComprador());
            comp.setNome(lote.getComprador().getNome());
            comp.setDocumento(lote.getComprador().getDocumento());
            comp.setContato(lote.getComprador().getContato());
            dto.setComprador(comp);
        }
        return dto;
    }

    private Lote fromDTO(LoteDTO dto) {
        Lote lote = new Lote();
        lote.setTipoSemente(dto.getTipoSemente());
        lote.setQuantidade(dto.getQuantidade());
        lote.setDataEntrada(dto.getDataEntrada());
        lote.setDataSaida(dto.getDataSaida());

        if (dto.getEmpresaDona() != null) {
            Empresa emp = new Empresa();
            emp.setIdEmpresa(dto.getEmpresaDona().getIdEmpresa());
            emp.setNome(dto.getEmpresaDona().getNome());
            emp.setTipo(dto.getEmpresaDona().getTipo());
            lote.setEmpresaDona(emp);
        }
        if (dto.getComprador() != null) {
            Comprador comp = new Comprador();
            comp.setIdComprador(dto.getComprador().getIdComprador());
            comp.setNome(dto.getComprador().getNome());
            comp.setDocumento(dto.getComprador().getDocumento());
            comp.setContato(dto.getComprador().getContato());
            lote.setComprador(comp);
        }
        return lote;
    }
}