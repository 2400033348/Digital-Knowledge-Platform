
package com.ps029.usageservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ps029.usageservice.entity.UsageRecord;
import com.ps029.usageservice.repository.UsageRepository;

@Service
public class UsageService {

    private final UsageRepository usageRepository;

    public UsageService(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    // CREATE: Record user activity
    public UsageRecord createUsageRecord(UsageRecord record) {
        return usageRepository.save(record);
    }

    // READ: Get all activity records
    public List<UsageRecord> getAllUsageRecords() {
        return usageRepository.findAll();
    }

    // READ: Get activity by ID
    public UsageRecord getUsageRecordById(Long id) {
        return usageRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usage record not found with ID: " + id
                    )
                );
    }

    // UPDATE: Update activity details
    public UsageRecord updateUsageRecord(Long id,
                                         UsageRecord newRecord) {

        UsageRecord existing = getUsageRecordById(id);

        existing.setUsername(newRecord.getUsername());
        existing.setContentId(newRecord.getContentId());
        existing.setActivityType(newRecord.getActivityType());
        existing.setDetails(newRecord.getDetails());

        return usageRepository.save(existing);
    }

    // DELETE: Delete activity record
    public void deleteUsageRecord(Long id) {

        UsageRecord existing = getUsageRecordById(id);

        usageRepository.delete(existing);
    }
}
