package co.istad.sms.features.user;

import co.istad.sms.features.user.dto.CreateUserRequest;

public interface UserService {
    void register(CreateUserRequest createUserRequest);
}
