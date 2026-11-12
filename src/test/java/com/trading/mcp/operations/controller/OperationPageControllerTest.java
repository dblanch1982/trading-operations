package com.trading.mcp.operations.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.trading.mcp.operations.dto.OperationDto;
import com.trading.mcp.operations.service.OperationService;

class OperationPageControllerTest {

    private OperationService operationService;
    private OperationPageController operationPageController;

    @BeforeEach
    void setUp() {
        operationService = mock(OperationService.class);
        operationPageController = new OperationPageController(operationService);
    }

    @Test
    void getOperationsPageAddsOperationsToModelAndReturnsIndexView() {
        List<OperationDto> operations = List.of(mock(OperationDto.class));
        Model model = mock(Model.class);
        when(operationService.getAllOperations()).thenReturn(operations);

        String view = operationPageController.getOperationsPage(model);

        assertEquals("index", view);
        verify(operationService).getAllOperations();
        verify(model).addAttribute("operations", operations);
    }
}
