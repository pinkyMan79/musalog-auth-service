package one.terenin.mapper;

import one.terenin.dto.response.TokenResponse;
import one.terenin.entity.TokenEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    @Mapping(source = "role", target = "role")
    TokenEntity map(TokenResponse response);
    @InheritInverseConfiguration
    TokenResponse map(TokenEntity entity);
}
