package lancamento_de_produtos.model.enums;

import lombok.Getter;

@Getter
public enum CargoFuncionario {
    
    ASSISTENTE("Assistente Administrativo"),
    GERENTE("Gerente"),
    COORDENADOR("Coordenador"),
    ESTAGIARIO("Estagiário"),
    MENOR_APRENDIZ("Menor Aprendiz");

    private final String descricao;

    
    CargoFuncionario(String descricao) {
        this.descricao = descricao;
    }
}
