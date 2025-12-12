package lancamento_de_produtos.model.entity;


import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lancamento_de_produtos.model.converter.CargoFuncionarioConverter;
import lancamento_de_produtos.model.enums.CargoFuncionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_funcionario")
@Getter @Setter @NoArgsConstructor
public class Funcionarios {

    @Id
    @Column(name = "matricula", unique = true, length = 10, nullable = false)
    private String registration;

    @Column(name = "nome_funcionario", length = 150, nullable = false)
    private String name;

    @Convert(converter = CargoFuncionarioConverter.class)
    @Column(name = "cargo", length = 50, nullable = false)
    private CargoFuncionario position;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dateBirth;
}