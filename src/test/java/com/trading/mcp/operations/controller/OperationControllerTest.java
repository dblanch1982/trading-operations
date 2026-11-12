package com.trading.mcp.operations.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.trading.mcp.operations.dto.OperationDto;
import com.trading.mcp.operations.model.OrderSide;
import com.trading.mcp.operations.model.TradingOperation;
import com.trading.mcp.operations.service.OperationService;

class OperationControllerTest {

    private OperationService operationService;
    private OperationController operationController;

    @BeforeEach
    void setUp() {
        operationService = mock(OperationService.class);
        operationController = new OperationController(operationService);
    }

    @Test
    void getAllOperationsAddsOperationsToModelAndReturnsIndexView() {
        List<TradingOperation> operations = List.of(operation());
        Model model = mock(Model.class);
        when(operationService.getAllOperations()).thenReturn(operations);

        String view = operationController.getAllOperations(model);

        assertEquals("index", view);
        verify(model).addAttribute("operations", operations);
    }

    @Test
    void getOperationsApiMapsOperationsToDtos() {
        TradingOperation operation = operation();
        when(operationService.getAllOperations()).thenReturn(List.of(operation));

        List<OperationDto> result = operationController.getOperationsApi();

        assertEquals(1, result.size());
        assertEquals(operation.id(), result.get(0).getId());
        assertEquals(operation.symbol(), result.get(0).getSymbol());
        assertEquals(operation.side(), result.get(0).getSide());
        assertEquals(operation.quantity(), result.get(0).getQuantity());
        assertEquals(operation.price(), result.get(0).getPrice());
        assertEquals(operation.timestamp(), result.get(0).getTimestamp());
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
}
