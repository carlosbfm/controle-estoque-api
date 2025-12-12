package lancamento_de_produtos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lancamento_de_produtos.dto.FuncionarioRequestDTO;
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
        Funcionarios func = new Funcionarios();
        
        String matriculaGerada;
        do {
            matriculaGerada = lancamento_de_produtos.utils.GeradorDeMatricula.gerarMatricula(8); 
        } while (repository.existsByRegistration(matriculaGerada));

        func.setRegistration(matriculaGerada);
        func.setName(dto.name());
        func.setPosition(dto.position());
        func.setDateBirth(dto.dateBith());

        return repository.save(func);
    }

    @Transactional
    public void atualizarPorPosicao(CargoFuncionario posicaoAlvo, FuncionarioRequestDTO dto) {
        List<Funcionarios> listaFuncionarios = repository.findByPosition(posicaoAlvo);

        if (listaFuncionarios.isEmpty()) {
            throw new IllegalArgumentException("Nenhum funcionário encontrado com a posição: " + posicaoAlvo);
        }

        for (Funcionarios func : listaFuncionarios) {
                if (dto.dateBith() != null) {
                func.setDateBirth(dto.dateBith());
            }

            if (dto.name() != null && !dto.name().isBlank()) {
                func.setName(dto.name());
            }
        }

        repository.saveAll(listaFuncionarios);
    }

    @Transactional
    public Funcionarios atualizarPorMatricula(String matriculaAtual, FuncionarioRequestDTO dto) {
        
        Funcionarios funcionario = repository.findByRegistration(matriculaAtual)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));

        funcionario.setName(dto.name());
        funcionario.setPosition(dto.position());
        funcionario.setDateBirth(dto.dateBith());

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
