package id.my.hendisantika.rbac.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> createOrder(@RequestBody Object orderDto) {
        // Implementation would go here
        return ResponseEntity.ok("Order created successfully - Requires 'order:write' permission");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Object orderDto) {
        // Implementation would go here
        return ResponseEntity.ok("Order updated successfully - Requires 'order:write' permission");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('order:delete')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        // Implementation would go here
        return ResponseEntity.ok("Order deleted successfully - Requires 'order:delete' permission");
    }
}
