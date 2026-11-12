package com.trading.mcp.operations.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.trading.mcp.operations.model.OrderSide;
import com.trading.mcp.operations.model.TradingOperation;
import com.trading.mcp.operations.repository.OperationRepository;
import com.trading.mcp.operations.dto.OperationDto;

class OperationServiceTest {

    private OperationRepository operationRepository;
    private OperationService operationService;

    @BeforeEach
    void setUp() {
        operationRepository = mock(OperationRepository.class);
        operationService = new OperationService(operationRepository);
    }

    @Test
    void getAllOperationsReturnsRepositoryResults() {
        TradingOperation tradingOperation = operation();
        when(operationRepository.findAll()).thenReturn(List.of(tradingOperation));

        List<OperationDto> operations = operationService.getAllOperations();
        assertEquals(1, operations.size());
        assertEquals(tradingOperation.getId(), operations.get(0).getId());
        assertEquals(tradingOperation.getSymbol(), operations.get(0).getSymbol());
        verify(operationRepository).findAll();
    }

    @Test
    void getOperationByIdReturnsOperationWhenFound() {
        TradingOperation operation = operation();
        when(operationRepository.findById(operation.getId())).thenReturn(Optional.of(operation));

        OperationDto result = operationService.getOperationById(operation.getId());
        assertEquals(operation.getId(), result.getId());
        assertEquals(operation.getSymbol(), result.getSymbol());
        verify(operationRepository).findById(operation.getId());
    }

    @Test
    void getOperationByIdReturnsNullWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(operationRepository.findById(id)).thenReturn(Optional.empty());

        assertNull(operationService.getOperationById(id));
        verify(operationRepository).findById(id);
    }

    @Test
    void saveOperationPersistsOperation() {
        OperationDto operation = dto();

        operationService.saveOperation(operation);

        ArgumentCaptor<TradingOperation> savedOperation = ArgumentCaptor.forClass(TradingOperation.class);
        verify(operationRepository).save(savedOperation.capture());
        assertEquals(operation.getId(), savedOperation.getValue().getId());
        assertEquals(operation.getSymbol(), savedOperation.getValue().getSymbol());
        assertEquals(operation.getSide(), savedOperation.getValue().getSide());
        assertEquals(operation.getQuantity(), savedOperation.getValue().getQuantity());
        assertEquals(operation.getPrice(), savedOperation.getValue().getPrice());
        assertEquals(operation.getTimestamp(), savedOperation.getValue().getTimestamp());
    }

    @Test
    void deleteOperationDeletesById() {
        UUID id = UUID.randomUUID();

        operationService.deleteOperation(id);

        verify(operationRepository).deleteById(id);
    }

    private TradingOperation operation() {
        return new TradingOperation(
                UUID.randomUUID(),
                "AAPL",
                OrderSide.BUY,
                2.0,
                150.0,
                Instant.parse("2026-01-01T10:00:00Z"));
    }

    private OperationDto dto() {
        TradingOperation operation = operation();
        return new OperationDto(
                operation.getId(),
                operation.getSymbol(),
                operation.getSide(),
                operation.getQuantity(),
                operation.getPrice(),
                operation.getTimestamp());
    }
}
