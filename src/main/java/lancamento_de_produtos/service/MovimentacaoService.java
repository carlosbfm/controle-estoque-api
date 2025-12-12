package lancamento_de_produtos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lancamento_de_produtos.dto.MovimentacaoRequestDTO;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.entity.Movimentacao;
import lancamento_de_produtos.model.entity.Produtos;
import lancamento_de_produtos.model.enums.TipoMovimentacao;
import lancamento_de_produtos.repository.FuncionariosRepository;
import lancamento_de_produtos.repository.MovimentacaoRepository;
import lancamento_de_produtos.repository.ProdutosRepository;

@Service
public class MovimentacaoService {
    private final MovimentacaoRepository movimentacaoRepo;
    private final ProdutosRepository produtoRepo;
    private final FuncionariosRepository funcionarioRepo;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository movimentacaoRepo,
                               ProdutosRepository produtoRepo,
                               FuncionariosRepository funcionarioRepo) {
        this.movimentacaoRepo = movimentacaoRepo;
        this.produtoRepo = produtoRepo;
        this.funcionarioRepo = funcionarioRepo;
    }

    @Transactional
    public Movimentacao registrar(MovimentacaoRequestDTO dto) {
        Produtos product = produtoRepo.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        Funcionarios employee = funcionarioRepo.findByRegistration(dto.employeeRegistration())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (dto.type() == TipoMovimentacao.ENTRADA) {
            product.setQuantity(product.getQuantity() + dto.quantity());
        } else {
            if (product.getQuantity() < dto.quantity()) {
                throw new IllegalArgumentException("Estoque insuficiente.");
            }
            product.setQuantity(product.getQuantity() - dto.quantity());
        }

        produtoRepo.save(product);

        Movimentacao mov = new Movimentacao();
        mov.setProduct(product);
        mov.setEmployee(employee);
        mov.setQuantity(dto.quantity());
        mov.setType(dto.type());
        mov.setDateRegister(java.time.LocalDateTime.now());

        return movimentacaoRepo.save(mov);
    }

    public List<Movimentacao> buscarPorProduto(String codigoProduto) {
        Produtos produto = produtoRepo.findById(codigoProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        
        return movimentacaoRepo.findByProduct(produto);
    }
    
    public List<Movimentacao> buscarPorMatricula(String matricula) {
        Funcionarios funcionario = funcionarioRepo.findByRegistration(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        return movimentacaoRepo.findByEmployee(funcionario);
    }

    public List<Movimentacao> buscarPorTipo(TipoMovimentacao tipo) {
        return movimentacaoRepo.findByType(tipo);
    }

    public List<Movimentacao> buscarPorData(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);
        return movimentacaoRepo.findByDateRegisterBetween(inicio, fim);
    }

    @Transactional
    public void deletar(Long id) {
        Movimentacao mov = movimentacaoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimentação não encontrada."));

        Produtos product = mov.getProduct();

        if (mov.getType() == TipoMovimentacao.ENTRADA) {
            if (product.getQuantity() < mov.getQuantity()) {
                throw new IllegalArgumentException("Não é possível cancelar a entrada: estoque atual insuficiente para o estorno.");
            }
            product.setQuantity(product.getQuantity() - mov.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() + mov.getQuantity());
        }

        produtoRepo.save(product);
        movimentacaoRepo.delete(mov);
    }
}
