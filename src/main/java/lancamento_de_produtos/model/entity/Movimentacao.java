package lancamento_de_produtos.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JoinColumn(name = "codigo", nullable = false)
    private Produtos product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula", nullable = false)
    private Funcionarios employee;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacao type;

    @CreatedDate
    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateUpdate;
}