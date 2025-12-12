package lancamento_de_produtos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lancamento_de_produtos.dto.MovimentacaoRequestDTO;
import lancamento_de_produtos.model.entity.Movimentacao;
import lancamento_de_produtos.model.enums.TipoMovimentacao;
import lancamento_de_produtos.service.MovimentacaoService;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;

    @Autowired
    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Movimentacao> registrar(@RequestBody MovimentacaoRequestDTO dto) {
        Movimentacao novaMovimentacao = service.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMovimentacao);
    }

    
    @GetMapping("/funcionario/{matricula}")
    public ResponseEntity<List<Movimentacao>> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(service.buscarPorMatricula(matricula));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<Movimentacao>> buscarPorTipo(@RequestParam TipoMovimentacao tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    @GetMapping("/data")
    public ResponseEntity<List<Movimentacao>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(service.buscarPorData(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
