package br.com.techsync.service;

import br.com.techsync.models.Empresa;
import br.com.techsync.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) { this.empresaRepository = empresaRepository; }

    public Empresa criarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa editarEmpresa(int id, Empresa empresa) {
        Optional<Empresa> empresaExistente = empresaRepository.findById(id);

        if (empresaExistente.isEmpty()) {
            return null;
        }

        Empresa e = empresaExistente.get();

        e.setNome(empresa.getNome());
        e.setCnpj(empresa.getCnpj());
        e.setCurrency(empresa.getCurrency());
        e.setLogo(empresa.getLogo());
        e.setTimezone(empresa.getTimezone());

        return empresaRepository.save(e);
    }

    public Empresa listarEmpresa(int id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        return empresa.orElse(null);
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public boolean excluirEmpresa(int id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
