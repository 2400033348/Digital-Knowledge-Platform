
package com.ps029.usageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps029.usageservice.entity.UsageRecord;

@Repository
public interface UsageRepository
        extends JpaRepository<UsageRecord, Long> {

}
