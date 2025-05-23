package br.com.techsync.controller;

import br.com.techsync.models.Usuario;
import br.com.techsync.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    // Editar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        Usuario usuarioEditado = usuarioService.editarUsuario(id, usuario);
        return usuarioEditado != null ? ResponseEntity.ok(usuarioEditado) : ResponseEntity.notFound().build();
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Excluir um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable int id) {
        return usuarioService.excluirUsuario(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

