package lancamento_de_produtos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lancamento_de_produtos.dto.FuncionarioRequestDTO;
import lancamento_de_produtos.mapper.FuncionariosMapper;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.enums.CargoFuncionario;
import lancamento_de_produtos.repository.FuncionariosRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    
    private final FuncionariosRepository repository;
    private final FuncionariosMapper mapper;

    @Transactional
    public Funcionarios cadastrar(FuncionarioRequestDTO dto) {
        Funcionarios func = mapper.toEntity(dto);
        
        String matriculaGerada;
        do {
            matriculaGerada = lancamento_de_produtos.utils.GeradorDeMatricula.gerarMatricula(8); 
        } while (repository.existsByRegistration(matriculaGerada));

        func.setRegistration(matriculaGerada);

        return repository.save(func);
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