package com.trading.mcp.operations.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trading.mcp.operations.dto.OperationDto;
import com.trading.mcp.operations.service.OperationService;

@RestController
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/api/operations")
    public List<OperationDto> getOperationsApi() {
        return operationService.getAllOperations();
    }

    @PostMapping("/api/operations")
    public ResponseEntity<Void> saveOperation(@RequestBody OperationDto operation) {
        operationService.saveOperation(operation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/operations/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable UUID id) {
        operationService.deleteOperation(id);
        return ResponseEntity.noContent().build();
    }
}
