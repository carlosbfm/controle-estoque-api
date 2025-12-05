package lancamento_de_produtos.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lancamento_de_produtos.model.entity.Movimentacao;
import lancamento_de_produtos.model.enums.TipoMovimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>{
    
    List<Movimentacao> findByType(TipoMovimentacao type);
    
    List<Movimentacao> findByDateRegisterBetween(LocalDateTime inicio, LocalDateTime fim);
    
    @Query(value = "SELECT * FROM tb_movimentacao WHERE CAST(data_cadastro AS DATE) = :data", nativeQuery = true)
    List<Movimentacao> findByDateRegisterOnly(@Param("data") LocalDate data);

}
