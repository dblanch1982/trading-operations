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
        assertEquals(tradingOperation.id(), operations.get(0).getId());
        assertEquals(tradingOperation.symbol(), operations.get(0).getSymbol());
        verify(operationRepository).findAll();
    }

    @Test
    void getOperationByIdReturnsOperationWhenFound() {
        TradingOperation operation = operation();
        when(operationRepository.findById(operation.id())).thenReturn(Optional.of(operation));

        OperationDto result = operationService.getOperationById(operation.id());
        assertEquals(operation.id(), result.getId());
        assertEquals(operation.symbol(), result.getSymbol());
        verify(operationRepository).findById(operation.id());
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
        assertEquals(operation.getId(), savedOperation.getValue().id());
        assertEquals(operation.getSymbol(), savedOperation.getValue().symbol());
        assertEquals(operation.getSide(), savedOperation.getValue().side());
        assertEquals(operation.getQuantity(), savedOperation.getValue().quantity());
        assertEquals(operation.getPrice(), savedOperation.getValue().price());
        assertEquals(operation.getTimestamp(), savedOperation.getValue().timestamp());
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
                operation.id(),
                operation.symbol(),
                operation.side(),
                operation.quantity(),
                operation.price(),
                operation.timestamp());
    }
}
