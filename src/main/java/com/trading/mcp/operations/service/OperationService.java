package com.trading.mcp.operations.service;


import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import com.trading.mcp.operations.model.TradingOperation;
import com.trading.mcp.operations.repository.OperationRepository;
import com.trading.mcp.operations.dto.OperationDto;


@Service
public class OperationService {

    Logger logger = Logger.getLogger(OperationService.class.getName());
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
        TradingOperation tradingOperation = toEntity(operation);
        operationRepository.save(tradingOperation);
        executeOperation("Added " + tradingOperation.getSide(), tradingOperation);
    }


    public void deleteOperation(UUID id) {
        operationRepository.deleteById(id);
    }   

    

    public void executeOperation(String operationType, TradingOperation operation) {
        logger.info("=== Ausführung der Operation: " + operationType + " ===");
        logger.info("ID: " + operation.getId());
        logger.info("Asset: " + operation.getSymbol());
        logger.info("Aktion: " + operation.getSide());

        // Mathematische Berechnung des Gesamtvolumens (Menge * Preis)
        double totalVolume = operation.getQuantity() * operation.getPrice();

        logger.info(String.format("Volumen: %.2f Einheiten zu je %.2f € (Gesamt: %.2f €)",
                operation.getQuantity(), operation.getPrice(), totalVolume));
        logger.info("Zeitstempel: " + operation.getTimestamp());
        logger.info("================================\n");
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