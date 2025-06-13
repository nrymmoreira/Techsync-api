package br.com.techsync.service;

import br.com.techsync.models.Usuario;
import br.com.techsync.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario criarUsuario(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public Usuario editarUsuario(int id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario u = usuarioExistente.get();
            u.setNome(usuario.getNome());
            u.setEmail(usuario.getEmail());

            if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
                String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
                u.setSenha(senhaCriptografada);
            }

            return usuarioRepository.save(u);
        }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public boolean excluirUsuario(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario buscarUsuarioId(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public boolean resetSenha(String email, String novaSenha){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaCriptografada);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean gerarCodigo2FA(String email){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();

            String codigo2FA = String.format("%06d", new Random().nextInt(999999));
            usuario.setCodigo2FA(codigo2FA);

            usuarioRepository.save(usuario);

            System.out.println("Codigo gerado = " + codigo2FA);

            return true;
        }
        return false;
    }

    public boolean autenticar2FA(String email, String senha, String codigo2FA){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();

            boolean senhaValida = passwordEncoder.matches(senha, usuario.getSenha());
            boolean codigoValido = codigo2FA.equals(usuario.getCodigo2FA());

            return senhaValida && codigoValido;
            }
        return false;
    }
}
