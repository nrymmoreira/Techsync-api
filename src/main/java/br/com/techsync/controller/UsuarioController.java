package br.com.techsync.controller;

import br.com.techsync.models.Usuario;
import br.com.techsync.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final String chaveSecreta = "MinhaChaveSuperSecreta1234567890123456";


    @Autowired
    private UsuarioService usuarioService;

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Map<String, String> usuario) {
        // Simular validação e salvamento no banco
        String nome = usuario.get("nome");
        String email = usuario.get("email");
        String senha = usuario.get("senha");

        if (nome == null || email == null || senha == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dados incompletos"));
        }

        // Aqui você implementaria a lógica real de salvamento no banco
        // Exemplo: Verificar se email já existe, salvar usuário, etc.
        return ResponseEntity.ok(Map.of("message", "Usuário criado com sucesso"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        if (email == null || senha == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Credenciais incompletas"));
        }

        // Simular validação no banco
        // Substitua por lógica real (ex.: verificar email e senha no banco)
        String token = Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, chaveSecreta.getBytes())
                .compact();
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of("id", 1, "nome", "Teste", "email", email));
        return ResponseEntity.ok(response);
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

}

