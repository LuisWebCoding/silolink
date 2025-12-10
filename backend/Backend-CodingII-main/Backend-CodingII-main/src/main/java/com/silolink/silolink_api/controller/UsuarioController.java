package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.UsuarioDTO;
import com.silolink.silolink_api.dto.EmpresaDTO;
import com.silolink.silolink_api.model.Usuario;
import com.silolink.silolink_api.model.Empresa;
import com.silolink.silolink_api.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(toDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = fromDTO(dto);
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        return usuarioRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    existente.setCargo(dto.getCargo());
                    existente.setEmail(dto.getEmail());
                    existente.setNivelAcesso(dto.getNivelAcesso());

                    // Atualiza senha apenas se vier preenchida
                    if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                        existente.setSenha(dto.getSenha());
                    }

                    if (dto.getEmpresa() != null) {
                        EmpresaDTO empDto = dto.getEmpresa();
                        Empresa empresa = existente.getEmpresa();
                        if (empresa == null) {
                            empresa = new Empresa();
                        }
                        // Nota: Aqui estamos assumindo a atualização dos dados da empresa aninhada
                        // Se fosse apenas trocar a empresa, usaríamos apenas o ID.
                        // Mas seguindo seu modelo de referência:
                        empresa.setIdEmpresa(empDto.getIdEmpresa()); // Importante para referenciar existente
                        empresa.setNome(empDto.getNome());
                        empresa.setTipo(empDto.getTipo());
                        existente.setEmpresa(empresa);
                    }

                    Usuario atualizado = usuarioRepository.save(existente);
                    return ResponseEntity.ok(toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Conversões
    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setCargo(usuario.getCargo());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        dto.setNivelAcesso(usuario.getNivelAcesso());

        if (usuario.getEmpresa() != null) {
            EmpresaDTO empDto = new EmpresaDTO();
            empDto.setIdEmpresa(usuario.getEmpresa().getIdEmpresa());
            empDto.setNome(usuario.getEmpresa().getNome());
            empDto.setTipo(usuario.getEmpresa().getTipo());
            dto.setEmpresa(empDto);
        }
        return dto;
    }

    private Usuario fromDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCargo(dto.getCargo());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setNivelAcesso(dto.getNivelAcesso());

        if (dto.getEmpresa() != null) {
            EmpresaDTO empDto = dto.getEmpresa();
            Empresa empresa = new Empresa();
            empresa.setIdEmpresa(empDto.getIdEmpresa());
            empresa.setNome(empDto.getNome());
            empresa.setTipo(empDto.getTipo());
            usuario.setEmpresa(empresa);
        }
        return usuario;
    }
}