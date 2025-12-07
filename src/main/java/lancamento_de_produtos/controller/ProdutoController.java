package lancamento_de_produtos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lancamento_de_produtos.dto.ProdutoRequestDTO;
import lancamento_de_produtos.model.entity.Produtos;
import lancamento_de_produtos.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    @Autowired
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Produtos> cadastrar(@RequestBody ProdutoRequestDTO dto) {
        Produtos novoProduto = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Produtos>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<Void> atualizarNome(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarNome(id, dto.name());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/descricao")
    public ResponseEntity<Void> atualizarDescricao(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarDescricao(id, dto.description());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/preco-venda")
    public ResponseEntity<Void> atualizarPrecoVenda(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarPrecoVenda(id, dto.priceSale());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/preco-custo")
    public ResponseEntity<Void> atualizarPrecoCusto(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarPrecoCusto(id, dto.priceCost());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarQuantidadeEstoque(id, dto.quantity());
        return ResponseEntity.noContent().build();
    }
}
