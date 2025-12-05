package lancamento_de_produtos.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lancamento_de_produtos.model.enums.TipoMovimentacao;

public record MovimentacaoRequestDTO(
    @NotNull(message = "O ID do produto é obrigatório")
    UUID productId,

    @NotNull(message = "O ID do funcionário é obrigatório")
    Long employeeId,

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    Integer quantity,

    @NotNull(message = "O tipo da movimentação é obrigatório")
    TipoMovimentacao type
) {}
