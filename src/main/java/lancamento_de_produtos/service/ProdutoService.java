package lancamento_de_produtos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lancamento_de_produtos.dto.ProdutoRequestDTO;
import lancamento_de_produtos.model.entity.Produtos;
import lancamento_de_produtos.repository.ProdutosRepository;

@Service
public class ProdutoService {
    private final ProdutosRepository repository;

    @Autowired
    public ProdutoService(ProdutosRepository repository) {
        this.repository = repository;
    }

    public Produtos cadastrar(ProdutoRequestDTO dto) {
        if (repository.existsByName(dto.name())) {
             throw new IllegalArgumentException("Produto já cadastrado com este nome.");
        }

        Produtos prod = new Produtos();
        prod.setName(dto.name());
        prod.setDescription(dto.name());
        prod.setPriceCost(dto.priceCost());
        prod.setPriceSale(dto.priceSale());
        
        prod.setQuantity(0);

        return repository.save(prod);
    }
}
