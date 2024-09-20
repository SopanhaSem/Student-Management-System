package co.istad.sms.features.user;

import co.istad.sms.features.user.dto.CreateUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        userService.register(createUserRequest);
    }
}
