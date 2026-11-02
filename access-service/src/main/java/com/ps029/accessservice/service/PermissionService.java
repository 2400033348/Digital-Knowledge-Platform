
package com.ps029.accessservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ps029.accessservice.entity.Permission;
import com.ps029.accessservice.repository.PermissionRepository;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    // CREATE: Grant permission
    public Permission grantPermission(Permission permission) {

        if (permission.getStatus() == null ||
                permission.getStatus().isBlank()) {
            permission.setStatus("GRANTED");
        }

        return permissionRepository.save(permission);
    }

    // READ: Get all permissions
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // READ: Get permission by ID
    public Permission getPermissionById(Long id) {

        return permissionRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Permission not found with ID: " + id
                    )
                );
    }

    // UPDATE: Update permission
    public Permission updatePermission(Long id, Permission newPermission) {

        Permission existing = getPermissionById(id);

        existing.setUsername(newPermission.getUsername());
        existing.setContentId(newPermission.getContentId());
        existing.setPermissionType(newPermission.getPermissionType());
        existing.setStatus(newPermission.getStatus());

        return permissionRepository.save(existing);
    }

    // DELETE: Revoke permission
    public void revokePermission(Long id) {

        Permission existing = getPermissionById(id);

        permissionRepository.delete(existing);
    }
}
