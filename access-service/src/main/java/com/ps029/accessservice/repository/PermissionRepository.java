
package com.ps029.accessservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps029.accessservice.entity.Permission;

@Repository
public interface PermissionRepository
        extends JpaRepository<Permission, Long> {

}
