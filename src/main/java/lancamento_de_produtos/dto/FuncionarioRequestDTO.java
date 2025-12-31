package lancamento_de_produtos.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lancamento_de_produtos.model.enums.CargoFuncionario;

public record FuncionarioRequestDTO(  

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    String name,

    @NotNull(message = "Cargo é obrigatório")
    CargoFuncionario position,

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser no passado") 
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateBith) 
    {}