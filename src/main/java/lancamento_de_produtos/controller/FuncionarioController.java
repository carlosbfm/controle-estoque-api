package lancamento_de_produtos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lancamento_de_produtos.dto.FuncionarioRequestDTO;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.enums.CargoFuncionario;
import lancamento_de_produtos.service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    
    private final FuncionarioService service;

    @Autowired
    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Funcionarios> cadastrar(@RequestBody FuncionarioRequestDTO dto) {
        Funcionarios novoFuncionario = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Funcionarios> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(service.buscarPorMatricula(matricula));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Funcionarios>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @GetMapping("/cargo")
    public ResponseEntity<List<Funcionarios>> buscarPorCargo(@RequestParam CargoFuncionario cargo) {
        return ResponseEntity.ok(service.buscarPorCargo(cargo));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Funcionarios> atualizar(@PathVariable String matricula, @RequestBody FuncionarioRequestDTO dto) {
        Funcionarios atualizado = service.atualizarPorMatricula(matricula, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/cargo/{cargoAlvo}")
    public ResponseEntity<Void> atualizarEmMassa(@PathVariable CargoFuncionario cargoAlvo, @RequestBody FuncionarioRequestDTO dto) {
        service.atualizarPorPosicao(cargoAlvo, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {
        service.deletarPorMatricula(matricula);
        return ResponseEntity.noContent().build();
    }
}
