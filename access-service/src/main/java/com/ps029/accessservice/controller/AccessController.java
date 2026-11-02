
package com.ps029.accessservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import com.ps029.accessservice.entity.Permission;
import com.ps029.accessservice.service.PermissionService;

@RestController
public class AccessController {

    private final PermissionService permissionService;

    public AccessController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // Existing JWT authentication test
    @GetMapping("/access/check")
    public String checkAccess(Authentication authentication) {
        return "Access granted for user: " + authentication.getName();
    }

    // CREATE: Grant permission
    @PostMapping("/access/permissions")
    public ResponseEntity<Permission> grantPermission(
            @RequestBody Permission permission) {

        Permission saved =
                permissionService.grantPermission(permission);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ ALL: Get all permissions
    @GetMapping("/access/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {

        return ResponseEntity.ok(
                permissionService.getAllPermissions());
    }

    // READ ONE: Get permission by ID
    @GetMapping("/access/permissions/{id}")
    public ResponseEntity<Permission> getPermissionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                permissionService.getPermissionById(id));
    }

    // UPDATE: Update permission
    @PutMapping("/access/permissions/{id}")
    public ResponseEntity<Permission> updatePermission(
            @PathVariable Long id,
            @RequestBody Permission permission) {

        return ResponseEntity.ok(
                permissionService.updatePermission(id, permission));
    }
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/access/instance")
    public String getInstance() {
        return "Request handled by Access Service instance on port: "
                + serverPort;
    }

    // DELETE: Revoke permission
    @DeleteMapping("/access/permissions/{id}")
    public ResponseEntity<Void> revokePermission(
            @PathVariable Long id) {

        permissionService.revokePermission(id);

        return ResponseEntity.noContent().build();
    }
}
