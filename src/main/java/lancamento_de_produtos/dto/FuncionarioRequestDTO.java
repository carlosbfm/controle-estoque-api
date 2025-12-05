package lancamento_de_produtos.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lancamento_de_produtos.model.enums.CargoFuncionario;

public record FuncionarioRequestDTO(  
    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 10, message = "Matrícula deve ter no máximo 10 caracteres")
    String registration,

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    String name,

    @NotNull(message = "Cargo é obrigatório")
    CargoFuncionario position,

    @NotNull(message = "Data de nascimento é obrigatória")
    LocalDate dateBith) {
  
}