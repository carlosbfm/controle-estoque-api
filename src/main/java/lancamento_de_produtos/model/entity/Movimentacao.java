package lancamento_de_produtos.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lancamento_de_produtos.model.enums.TipoMovimentacao;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacao")
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor
public class Movimentacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produtos product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionarios employee;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacao type;

    @CreatedDate
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dateUpdate;
}