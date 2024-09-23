package co.istad.sms.init;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Department;
import co.istad.sms.domain.User;
import co.istad.sms.domain.UserProfile;
import co.istad.sms.features.course.CourseRepository;
import co.istad.sms.features.department.DepartmentRepository;
import co.istad.sms.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    @PostConstruct
    void init(){
        initUserData();
    }
    void initUserData(){
        User user = new User();
        user.setEmail("tribalc51@gmail.com");
        user.setUsername("Sopanha");
        user.setPassword(passwordEncoder.encode("qwer"));

        UserProfile userProfile = new UserProfile();
        userProfile.setFullName("Sem Sopanha");
        userProfile.setAddress("somewhere");
        userProfile.setPhoneNumber("093560288");

        user.setUserProfile(userProfile);
        userProfile.setUser(user);
        userRepository.save(user);
    }
}
