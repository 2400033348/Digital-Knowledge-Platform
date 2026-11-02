
package com.ps029.usageservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import com.ps029.usageservice.entity.UsageRecord;
import com.ps029.usageservice.service.UsageService;

@RestController
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    // CREATE: Record user activity
    @PostMapping("/usage/track")
    public ResponseEntity<UsageRecord> createUsageRecord(
            @RequestBody UsageRecord record) {

        UsageRecord saved =
                usageService.createUsageRecord(record);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // READ ALL: View activity history
    @GetMapping("/usage/history")
    public ResponseEntity<List<UsageRecord>> getAllUsageRecords() {

        return ResponseEntity.ok(
                usageService.getAllUsageRecords());
    }

    // READ ONE: View activity by ID
    @GetMapping("/usage/history/{id}")
    public ResponseEntity<UsageRecord> getUsageRecordById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                usageService.getUsageRecordById(id));
    }
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/usage/instance")
    public String getInstance() {
        return "Request handled by Usage Service instance on port: "
                + serverPort;
    }

    // UPDATE: Update activity
    @PutMapping("/usage/history/{id}")
    public ResponseEntity<UsageRecord> updateUsageRecord(
            @PathVariable Long id,
            @RequestBody UsageRecord record) {

        return ResponseEntity.ok(
                usageService.updateUsageRecord(id, record));
    }

    // DELETE: Delete activity
    @DeleteMapping("/usage/history/{id}")
    public ResponseEntity<Void> deleteUsageRecord(
            @PathVariable Long id) {

        usageService.deleteUsageRecord(id);

        return ResponseEntity.noContent().build();
    }
}
