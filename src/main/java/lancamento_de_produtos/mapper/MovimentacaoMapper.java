package lancamento_de_produtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lancamento_de_produtos.dto.MovimentacaoRequestDTO;
import lancamento_de_produtos.model.entity.Movimentacao;

@Mapper(componentModel = "spring", uses = MovimentacaoHelper.class)
public interface MovimentacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateRegister", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "dateUpdate", expression = "java(java.time.LocalDateTime.now())")
    
    @Mapping(target = "product", source = "productId")
    
    @Mapping(target = "employee", source = "employeeRegistration")
    Movimentacao toEntity(MovimentacaoRequestDTO dto);

    @Mapping(source = "product.codigo", target = "productId")
    @Mapping(source = "employee.registration", target = "employeeRegistration")
    MovimentacaoRequestDTO toDto(Movimentacao movimentacao);
}