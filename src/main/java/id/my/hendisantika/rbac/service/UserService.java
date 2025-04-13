package id.my.hendisantika.rbac.service;

import id.my.hendisantika.rbac.dto.UserRegistrationDto;
import id.my.hendisantika.rbac.model.Role;
import id.my.hendisantika.rbac.model.User;
import id.my.hendisantika.rbac.repository.RoleRepository;
import id.my.hendisantika.rbac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-rbac
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/04/25
 * Time: 07.31
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User registerNewUser(UserRegistrationDto userRegistrationDto) {

        // Check if username already exist
        if (userRepository.existsByUsername(userRegistrationDto.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }

        // Create new user
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        // Assign default role (USER)
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.addRole(userRole);
        return userRepository.save(user);
    }

    @Transactional
    public void assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.addRole(role);
        userRepository.save(user);
    }
}
