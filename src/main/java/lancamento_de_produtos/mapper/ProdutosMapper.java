package lancamento_de_produtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lancamento_de_produtos.dto.ProdutoRequestDTO;
import lancamento_de_produtos.model.entity.Produtos;

@Mapper(componentModel = "spring")
public interface ProdutosMapper {

    @Mapping(target = "codigo", ignore = true)
    Produtos toEntity(ProdutoRequestDTO dto);

    
    ProdutoRequestDTO toDto(Produtos produto);
}
