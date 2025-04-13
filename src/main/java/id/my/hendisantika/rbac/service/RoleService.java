package id.my.hendisantika.rbac.service;

import id.my.hendisantika.rbac.model.Permission;
import id.my.hendisantika.rbac.model.Role;
import id.my.hendisantika.rbac.repository.PermissionRepository;
import id.my.hendisantika.rbac.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
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
 * Time: 07.25
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public Role createRole(String name) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Role with name " + name + " already exists");
        }

        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Transactional
    public void assignPermissionToRole(String roleName, String permissionName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role with name " + roleName + " does not exist"));
        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission with name " + permissionName + " does not exist"));

        role.addPermission(permission);
        roleRepository.save(role);
    }

    @Transactional
    public void unassignPermissionFromRole(String roleName, String permissionName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role with name " + roleName + " does not exist"));
        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission with name " + permissionName + " does not exist"));

        role.getPermissions().remove(permission);
        roleRepository.save(role);
    }

    @Transactional
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

}
