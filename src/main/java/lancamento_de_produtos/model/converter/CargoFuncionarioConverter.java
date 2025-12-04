package lancamento_de_produtos.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lancamento_de_produtos.model.enums.CargoFuncionario;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CargoFuncionarioConverter implements AttributeConverter<CargoFuncionario, String> {

    @Override
    public String convertToDatabaseColumn(CargoFuncionario cargo) {
        return (cargo == null) ? null : cargo.getDescricao();
    }

    @Override
    public CargoFuncionario convertToEntityAttribute(String descricao) {
        if (descricao == null) return null;
        return Stream.of(CargoFuncionario.values())
                .filter(c -> c.getDescricao().equals(descricao))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cargo inválido: " + descricao));
    }
}