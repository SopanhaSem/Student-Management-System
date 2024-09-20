package co.istad.sms.features.user;

import co.istad.sms.domain.User;
import co.istad.sms.features.user.dto.CreateUserRequest;
import co.istad.sms.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public void register(CreateUserRequest createUserRequest) {

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email already exists");
        }
        if(userRepository.existsByUsername(createUserRequest.username())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,
                    "Username already exists");
        }

        User user = userMapper.fromCreateUserRequest(createUserRequest);
        user.setUserProfile(user.getUserProfile());
        userRepository.save(user);
    }
}
