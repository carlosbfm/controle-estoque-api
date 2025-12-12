package lancamento_de_produtos.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produto" , schema = "public")
@Data
@NoArgsConstructor
public class Produtos {

    @Id
    @Column(name = "codigo_estoque")
    private String codigo;

    @Column(name = "nome_do_produto", length = 100, nullable = false)
    private String name;

    @Column(name = "descricao", length = 300)
    private String description;

    @Column(name = "quantidade_atual")
    private Integer quantity;

    @Column(name = "preco_custo", precision = 18, scale = 2)
    private BigDecimal priceCost;

    @Column(name = "preco_venda", precision = 18, scale = 2)
    private BigDecimal priceSale;
}
