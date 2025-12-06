package lancamento_de_produtos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.enums.CargoFuncionario;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Long> {

    Optional<Funcionarios> findByRegistration(String registration);

    boolean existsByRegistration(String registration);

    List<Funcionarios> findByNameContainingIgnoreCase(String name);

    List<Funcionarios> findByPosition(CargoFuncionario position);


}
