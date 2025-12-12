package lancamento_de_produtos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{codigo}")
    public ResponseEntity<Produtos> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(service.buscarPorCodigo(codigo));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Produtos>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @PatchMapping("/{codigo}/nome")
    public ResponseEntity<Void> atualizarNome(@PathVariable String codigo, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarNome(codigo, dto.name());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{codigo}/descricao")
    public ResponseEntity<Void> atualizarDescricao(@PathVariable String codigo, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarDescricao(codigo, dto.description());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{codigo}/preco-custo")
    public ResponseEntity<Void> atualizarPrecoCusto(@PathVariable String codigo, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarPrecoCusto(codigo, dto.priceCost());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{codigo}/preco-venda")
    public ResponseEntity<Void> atualizarPrecoVenda(@PathVariable String codigo, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarPrecoVenda(codigo, dto.priceSale());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{codigo}/estoque")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable String codigo, @RequestBody ProdutoRequestDTO dto) {
        service.atualizarQuantidadeEstoque(codigo, dto.quantity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable String codigo) {
        service.deletar(codigo);
        return ResponseEntity.noContent().build();
    }
}