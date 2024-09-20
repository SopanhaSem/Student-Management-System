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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    @PostConstruct
    void init(){
        initUserData();
        initCourseData();
        initDepartmentData();
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
    void initDepartmentData() {
        Department department1 = new Department();
        department1.setDepartmentName("Mathematics");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName("Physics");
        departmentRepository.save(department2);

        Department department3 = new Department();
        department3.setDepartmentName("Chemistry");
        departmentRepository.save(department3);

        Department department4 = new Department();
        department4.setDepartmentName("Biology");
        departmentRepository.save(department4);
    }
    void initCourseData() {
        Course course1 = new Course();
        course1.setDepartments(course1.getDepartments());
        course1.setCourseName("Mathematics");
        course1.setCredits(3);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setCourseName("Physics");
        course2.setCredits(4);
        course2.setDepartments(course2.getDepartments());
        courseRepository.save(course2);

        Course course3 = new Course();
        course3.setCourseName("Chemistry");
        course3.setCredits(3);
        course3.setDepartments(course3.getDepartments());
        courseRepository.save(course3);

        Course course4 = new Course();
        course4.setCourseName("Biology");
        course4.setCredits(4);
        course4.setDepartments(course4.getDepartments());
        courseRepository.save(course4);
    }
}
