package com.trading.mcp.operations.service;


import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.trading.mcp.operations.model.TradingOperation;
import com.trading.mcp.operations.repository.OperationRepository;
import com.trading.mcp.operations.dto.OperationDto;


@Service
public class OperationService {

    private final OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }   

    public List<OperationDto> getAllOperations() {
        // Implement logic to retrieve all operations
        return operationRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }


    public OperationDto getOperationById(UUID id) {
        return operationRepository.findById(id).map(this::toDto).orElse(null);
    }

    public void saveOperation(OperationDto operation) {
        operationRepository.save(toEntity(operation));
    }


    public void deleteOperation(UUID id) {
        operationRepository.deleteById(id);
    }   

    private OperationDto toDto(TradingOperation operation) {
        return new OperationDto(
                operation.getId(),
                operation.getSymbol(),
                operation.getSide(),
                operation.getQuantity(),
                operation.getPrice(),
                operation.getTimestamp());
    }

    private TradingOperation toEntity(OperationDto operation) {
        return new TradingOperation(
                operation.getId(),
                operation.getSymbol(),
                operation.getSide(),
                operation.getQuantity(),
                operation.getPrice(),
                operation.getTimestamp());
    }
}