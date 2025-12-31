package lancamento_de_produtos.mapper;

import org.springframework.stereotype.Component;
import lancamento_de_produtos.model.entity.Funcionarios;
import lancamento_de_produtos.model.entity.Produtos;
import lancamento_de_produtos.repository.FuncionariosRepository;
import lancamento_de_produtos.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimentacaoHelper {

    private final ProdutosRepository produtosRepository;
    private final FuncionariosRepository funcionariosRepository;

    public Produtos buscarProduto(String codigo) {
        return produtosRepository.findById(codigo)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + codigo));
    }

    public Funcionarios buscarFuncionario(String matricula) {
        return funcionariosRepository.findByRegistration(matricula)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado: " + matricula));
    }
}