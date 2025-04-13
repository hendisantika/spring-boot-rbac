package id.my.hendisantika.rbac.config;

import id.my.hendisantika.rbac.model.Permission;
import id.my.hendisantika.rbac.model.Role;
import id.my.hendisantika.rbac.model.User;
import id.my.hendisantika.rbac.repository.PermissionRepository;
import id.my.hendisantika.rbac.repository.RoleRepository;
import id.my.hendisantika.rbac.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-rbac
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/04/25
 * Time: 07.43
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        // Create permissions
        Permission readUser = new Permission();
        readUser.setName("user:read");

        Permission writeUser = new Permission();
        writeUser.setName("user:write");

        Permission deleteUser = new Permission();
        deleteUser.setName("user:delete");

        Permission readProduct = new Permission();
        readProduct.setName("product:read");

        Permission writeProduct = new Permission();
        writeProduct.setName("product:write");

        Permission deleteProduct = new Permission();
        deleteProduct.setName("product:delete");

        Permission readOrder = new Permission();
        readOrder.setName("order:read");

        Permission writeOrder = new Permission();
        writeOrder.setName("order:write");

        Permission deleteOrder = new Permission();
        deleteOrder.setName("order:delete");

        permissionRepository.saveAll(Set.of(
                readUser, writeUser, deleteUser,
                readProduct, writeProduct, deleteProduct,
                readOrder, writeOrder, deleteOrder
        ));

        // Create roles and assign permissions
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setPermissions(Set.of(
                readUser, writeUser, deleteUser,
                readProduct, writeProduct, deleteProduct,
                readOrder, writeOrder, deleteOrder
        ));

        Role managerRole = new Role();
        managerRole.setName("MANAGER");
        managerRole.setPermissions(Set.of(
                readUser, writeUser,
                readProduct, writeProduct,
                readOrder, writeOrder
        ));

        Role userRole = new Role();
        userRole.setName("USER");
        userRole.setPermissions(Set.of(
                readUser,
                readProduct,
                readOrder
        ));

        roleRepository.saveAll(Set.of(adminRole, managerRole, userRole));

        // Create sample users
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRoles(Set.of(adminRole));

        User manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("password"));
        manager.setRoles(Set.of(managerRole));

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Set.of(userRole));

        userRepository.saveAll(Set.of(admin, manager, user));

        log.info("Sample data initialized successfully!");
    }
}
