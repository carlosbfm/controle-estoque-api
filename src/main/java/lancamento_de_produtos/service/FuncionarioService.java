package lancamento_de_produtos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lancamento_de_produtos.dto.FuncionarioRequestDTO;
import lancamento_de_produtos.exception.RegistrationDuplicateException;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.enums.CargoFuncionario;
import lancamento_de_produtos.repository.FuncionariosRepository;

@Service
public class FuncionarioService {
    
    private final FuncionariosRepository repository;

    @Autowired
    public FuncionarioService(FuncionariosRepository repository) {
        this.repository = repository;
    }

    @Transactional
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

    @Transactional
    public Funcionarios atualizarDadosPessoais(String resgistration, FuncionarioRequestDTO dto) {
        
        Funcionarios funcionario = repository.findByRegistration(resgistration)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));

        if (dto.name() != null) {
            funcionario.setName(dto.name());
        }

        if (dto.dateBith() != null) {
            funcionario.setDateBirth(dto.dateBith());
        }
        
        return repository.save(funcionario);
    }

    @Transactional
    public void atualizarPorPosicao(CargoFuncionario posicaoAlvo, FuncionarioRequestDTO dto) {
    List<Funcionarios> listaFuncionarios = repository.findByPosition(posicaoAlvo);

        if (listaFuncionarios.isEmpty()) {
            throw new IllegalArgumentException("Nenhum funcionário encontrado com a posição: " + posicaoAlvo);
        }

        for (Funcionarios func : listaFuncionarios) {
            if (dto.position() != null) {
                func.setPosition(dto.position());
            }
        }

        repository.saveAll(listaFuncionarios);
    }


    @Transactional
    public Funcionarios atualizarPorMatricula(String matriculaAtual, FuncionarioRequestDTO dto) {
        Funcionarios funcionario = repository.findByRegistration(matriculaAtual)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));

        if (dto.registration() != null && !dto.registration().equals(matriculaAtual)) {
            if (repository.existsByRegistration(dto.registration())) {
                throw new IllegalArgumentException("A nova matrícula já está em uso.");
            }
            funcionario.setRegistration(dto.registration());
        }

        return repository.save(funcionario);
    }

    public Funcionarios buscarPorMatricula(String matricula) {
        return repository.findByRegistration(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
    }

    public List<Funcionarios> buscarPorNome(String nome) {
        return repository.findByNameContainingIgnoreCase(nome);
    }

    public List<Funcionarios> buscarPorCargo(CargoFuncionario cargo) {
        return repository.findByPosition(cargo);
    }

    @Transactional
    public void deletarPorMatricula(String matricula) {
        Funcionarios funcionario = repository.findByRegistration(matricula)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));

        repository.delete(funcionario);
    }
}
