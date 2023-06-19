package one.terenin.mapper;

import one.terenin.dto.response.TokenResponse;
import one.terenin.entity.TokenEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    TokenEntity map(TokenResponse response);
    @InheritInverseConfiguration
    TokenResponse map(TokenEntity entity);
}
