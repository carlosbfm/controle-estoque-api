package lancamento_de_produtos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

    @Transactional
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

    @Transactional
    public void atualizarNome(UUID id, String novoNome) {
        Produtos produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (!produto.getName().equals(novoNome) && repository.existsByName(novoNome)) {
            throw new IllegalArgumentException("Já existe outro produto com este nome.");
        }

        produto.setName(novoNome);
        repository.save(produto);
    }

    @Transactional
    public void atualizarPrecoVenda(UUID id, BigDecimal novoPreco) {
        Produtos produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        
        if (novoPreco.compareTo(BigDecimal.ZERO) < 0) {
             throw new IllegalArgumentException("O preço de venda não pode ser negativo.");
        }

        produto.setPriceSale(novoPreco);
        repository.save(produto);
    }

    @Transactional
    public void atualizarPrecoCusto(UUID id, BigDecimal novoCusto) {
        Produtos produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (novoCusto.compareTo(BigDecimal.ZERO) < 0) {
             throw new IllegalArgumentException("O preço de custo não pode ser negativo.");
        }

        produto.setPriceCost(novoCusto);
        repository.save(produto);
    }

    @Transactional
    public void atualizarQuantidadeEstoque(UUID id, Integer novaQuantidade) {
        Produtos produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }

        produto.setQuantity(novaQuantidade);
        repository.save(produto);
    }

    @Transactional
    public void atualizarDescricao(UUID id, String novaDescricao) {
        Produtos produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        produto.setDescription(novaDescricao);
        repository.save(produto);
    }

    public Produtos buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
    }

    public List<Produtos> buscarPorNome(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public void deletar(UUID id) {
        Produtos produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        
        repository.delete(produto);
    }
}
