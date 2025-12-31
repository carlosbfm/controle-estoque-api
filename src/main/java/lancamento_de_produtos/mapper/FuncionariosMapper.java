package lancamento_de_produtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lancamento_de_produtos.dto.FuncionarioRequestDTO;
import lancamento_de_produtos.model.entity.Funcionarios;

@Mapper(componentModel = "spring")
public interface FuncionariosMapper {


    @Mapping(target = "registration",  ignore = true)
    @Mapping(source = "dateBith", target = "dateBirth")
    Funcionarios toEntity(FuncionarioRequestDTO dto);
    @Mapping(source = "dateBirth", target = "dateBith")
    FuncionarioRequestDTO toDto(Funcionarios funcionario);
}
