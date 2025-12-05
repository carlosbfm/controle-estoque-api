package lancamento_de_produtos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lancamento_de_produtos.dto.FuncionarioRequestDTO;
import lancamento_de_produtos.exception.RegistrationDuplicateException;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.repository.FuncionariosRepository;

@Service
public class FuncionarioService {
    private final FuncionariosRepository repository;

    @Autowired
    public FuncionarioService(FuncionariosRepository repository) {
        this.repository = repository;
    }

    public Funcionarios cadastrar(FuncionarioRequestDTO dto) {
        if (repository.findByRegistration(dto.registration()).isPresent()) {
            throw new RegistrationDuplicateException(dto.registration());
        }

        Funcionarios func = new Funcionarios();
        func.setRegistration(dto.registration());
        func.setName(dto.name());
        func.setPosition(dto.position());
        func.setDateBirth(dto.dateBith());

        return repository.save(func);
    }
}
