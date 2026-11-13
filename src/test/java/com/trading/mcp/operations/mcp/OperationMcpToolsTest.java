package com.trading.mcp.operations.mcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.trading.mcp.operations.dto.OperationDto;
import com.trading.mcp.operations.model.OrderSide;
import com.trading.mcp.operations.service.OperationService;

class OperationMcpToolsTest {

    private OperationService operationService;
    private OperationMcpTools operationMcpTools;

    @BeforeEach
    void setUp() {
        operationService = mock(OperationService.class);
        operationMcpTools = new OperationMcpTools(operationService);
    }

    @Test
    void listOperationsReturnsRecordedOperations() {
        List<OperationDto> operations = List.of(operation());
        when(operationService.getAllOperations()).thenReturn(operations);

        assertEquals(operations, operationMcpTools.listOperations());
        verify(operationService).getAllOperations();
    }

    @Test
    void getOperationReturnsOperationForValidId() {
        OperationDto operation = operation();
        when(operationService.getOperationById(operation.getId())).thenReturn(operation);

        assertEquals(operation, operationMcpTools.getOperation(operation.getId().toString()));
        verify(operationService).getOperationById(operation.getId());
    }

    @Test
    void getOperationRejectsInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> operationMcpTools.getOperation("invalid-id"));
    }

    @Test
    void getOperationRejectsUnknownId() {
        UUID id = UUID.randomUUID();
        when(operationService.getOperationById(id)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> operationMcpTools.getOperation(id.toString()));
        verify(operationService).getOperationById(id);
    }

    private OperationDto operation() {
        return new OperationDto(
                UUID.randomUUID(),
                "AAPL",
                OrderSide.BUY,
                2.0,
                150.0,
                Instant.parse("2026-01-01T10:00:00Z"));
    }
}
