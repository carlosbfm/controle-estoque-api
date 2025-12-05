package lancamento_de_produtos.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestDTO(
    @NotBlank(message = "Nome do produto é obrigatório")
    String name,

    String description,

    @NotNull(message = "Preço de custo é obrigatório")
    @Positive
    BigDecimal priceCost,

    @NotNull(message = "Preço de venda é obrigatório")
    @Positive
    BigDecimal priceSale
) 
{}
