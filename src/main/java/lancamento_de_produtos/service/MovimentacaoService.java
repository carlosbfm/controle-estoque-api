package lancamento_de_produtos.service;

import java.time.LocalDateTime;

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
        
        Funcionarios employee = funcionarioRepo.findById(dto.employeeId())
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (dto.type() == TipoMovimentacao.ENTRADA) {
            product.setQuantity(product.getQuantity() + dto.quantity());
        } 
        else {
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
        mov.setDateRegister(LocalDateTime.now());
        
        return movimentacaoRepo.save(mov);
    }
}
