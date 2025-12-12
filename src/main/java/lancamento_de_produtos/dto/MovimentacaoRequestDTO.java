package lancamento_de_produtos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lancamento_de_produtos.model.enums.TipoMovimentacao;

public record MovimentacaoRequestDTO(
    @NotBlank(message = "O código do produto é obrigatório")
    String productId,

    @NotBlank(message = "A matrícula do funcionário é obrigatória")
    String employeeRegistration,

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    Integer quantity,

    @NotNull(message = "O tipo da movimentação é obrigatório")
    TipoMovimentacao type
) {}
