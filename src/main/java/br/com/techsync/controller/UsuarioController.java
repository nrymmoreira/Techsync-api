package br.com.techsync.controller;

import br.com.techsync.models.Usuario;
import br.com.techsync.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Resetar Senha do usuário
    @PostMapping("/resetSenha")
    public ResponseEntity<String> resetSenha(@RequestParam String email, @RequestParam String novaSenha){
        boolean sucesso = usuarioService.resetSenha(email,novaSenha);

        if (sucesso){
            return ResponseEntity.ok("Senha alterada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    //Gerar codigo de autentificação de dois fatores (2FA)
    @PostMapping("/gerar2fa")
    public ResponseEntity<String> gerarCodigo2FA(@RequestParam String email){
        boolean gerado = usuarioService.gerarCodigo2FA(email);

        if(gerado){
            return ResponseEntity.ok("Codigo 2FA gerado com sucesso.");
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    //Realizando a autenticação de dois fatores (2FA)
    @PostMapping("/autenticar2fa")
    public ResponseEntity<String> autenticar2FA(
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String codigo2FA){
        boolean autenticado = usuarioService.autenticar2FA(email, senha, codigo2FA);

        if (autenticado){
            return ResponseEntity.ok("Autenticação 2FA bem-sucedida");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação 2FA.");
        }
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

    // Listar um único usuário por ID\
    @GetMapping ("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioID(@PathVariable int id){
        Usuario usuario = usuarioService.buscarUsuarioId(id);
        if (usuario != null){
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable int id) {
        return usuarioService.excluirUsuario(id)
                ? ResponseEntity.ok("Usuario excluido com sucesso!")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    }
}

