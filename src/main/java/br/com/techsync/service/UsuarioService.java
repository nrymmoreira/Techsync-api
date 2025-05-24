package br.com.techsync.service;

import br.com.techsync.models.Usuario;
import br.com.techsync.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario editarUsuario(int id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario u = usuarioExistente.get();
            u.setNome(usuario.getNome());
            u.setEmail(usuario.getEmail());
            u.setSenha(usuario.getSenha());
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
            usuario.setSenha(novaSenha);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}
