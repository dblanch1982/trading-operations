package com.trading.mcp.operations.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trading.mcp.operations.dto.OperationDto;
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
    void getOperationsApiMapsOperationsToDtos() {
        OperationDto operation = mock(OperationDto.class);
        List<OperationDto> operations = List.of(operation);
        when(operationService.getAllOperations()).thenReturn(operations);

        List<OperationDto> result = operationController.getOperationsApi();

        assertEquals(operations, result);
    }

    @Test
    void saveOperationDelegatesToServiceAndReturnsCreated() {
        OperationDto operation = mock(OperationDto.class);

        ResponseEntity<Void> response = operationController.saveOperation(operation);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(operationService).saveOperation(operation);
    }

    @Test
    void deleteOperationDelegatesToServiceAndReturnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = operationController.deleteOperation(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(operationService).deleteOperation(id);
    }
}
