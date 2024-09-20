package co.istad.sms.mapper;

import co.istad.sms.domain.User;
import co.istad.sms.features.auth.dto.RegisterRequest;
import co.istad.sms.features.user.dto.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromCreateUserRequest(CreateUserRequest createUserRequest);
    User fromRegisterRequest(RegisterRequest registerRequest);
}
