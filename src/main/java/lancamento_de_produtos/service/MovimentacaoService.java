package lancamento_de_produtos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lancamento_de_produtos.dto.MovimentacaoRequestDTO;
import lancamento_de_produtos.mapper.MovimentacaoMapper;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.entity.Movimentacao;
import lancamento_de_produtos.model.entity.Produtos;
import lancamento_de_produtos.model.enums.TipoMovimentacao;
import lancamento_de_produtos.repository.FuncionariosRepository;
import lancamento_de_produtos.repository.MovimentacaoRepository;
import lancamento_de_produtos.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {
    
    private final MovimentacaoRepository movimentacaoRepo;
    private final ProdutosRepository produtoRepo;
    private final FuncionariosRepository funcionarioRepo;
    private final MovimentacaoMapper mapper;

    @Transactional
    public Movimentacao registrar(MovimentacaoRequestDTO dto) {
        Movimentacao mov = mapper.toEntity(dto);
        
        Produtos product = mov.getProduct();

        if (mov.getType() == TipoMovimentacao.ENTRADA) {
            product.setQuantity(product.getQuantity() + mov.getQuantity());
        } else {
            if (product.getQuantity() < mov.getQuantity()) {
                throw new IllegalArgumentException("Estoque insuficiente.");
            }
            product.setQuantity(product.getQuantity() - mov.getQuantity());
        }

        produtoRepo.save(product);
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
                throw new IllegalArgumentException("Não é possível cancelar a entrada: estoque insuficiente para o estorno.");
            }
            product.setQuantity(product.getQuantity() - mov.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() + mov.getQuantity());
        }

        produtoRepo.save(product);
        movimentacaoRepo.delete(mov);
    }
}