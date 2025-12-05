package lancamento_de_produtos.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import lancamento_de_produtos.model.entity.Produtos;

public interface ProdutosRepository extends  JpaRepository<Produtos, UUID>{
    
    Optional<Produtos> findById(UUID id);

    List<Produtos>  findByName(String name);

    boolean existsByName(String name);

    List<Produtos>  findByPriceSale(BigDecimal priceSale);

    List<Produtos>  findByPriceCost(BigDecimal priceCost);

}
