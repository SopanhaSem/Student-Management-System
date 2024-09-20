package co.istad.sms.security;


import co.istad.sms.domain.User;
import co.istad.sms.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username: " + username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        return customUserDetails;
    }

}