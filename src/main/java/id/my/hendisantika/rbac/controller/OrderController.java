package id.my.hendisantika.rbac.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-rbac
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/04/25
 * Time: 07.40
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @GetMapping
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getAllOrders() {
        // Implementation would go here
        return ResponseEntity.ok("List of all orders - Requires 'order:read' permission");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        // Implementation would go here
        return ResponseEntity.ok("Order details for ID: " + id + " - Requires 'order:read' permission");
    }
}
