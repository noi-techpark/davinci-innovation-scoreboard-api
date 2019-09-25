package it.bz.davinci.innovationscoreboard.user;

import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import it.bz.davinci.innovationscoreboard.user.model.ApiUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({})
    public UserResponse to(ApiUser user);
}
