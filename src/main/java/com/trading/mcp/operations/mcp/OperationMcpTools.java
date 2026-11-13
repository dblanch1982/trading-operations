package com.trading.mcp.operations.mcp;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import com.trading.mcp.operations.dto.OperationDto;
import com.trading.mcp.operations.service.OperationService;

@Component
public class OperationMcpTools {

    private final OperationService operationService;

    public OperationMcpTools(OperationService operationService) {
        this.operationService = operationService;
    }

    @McpTool(
            name = "list_operations",
            description = "List recorded trading operations. Read-only; does not place or execute orders.",
            generateOutputSchema = true,
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = true,
                    destructiveHint = false,
                    idempotentHint = true))
    public List<OperationDto> listOperations() {
        return operationService.getAllOperations();
    }

    @McpTool(
            name = "get_operation",
            description = "Get a recorded trading operation by its UUID. Read-only; does not place or execute orders.",
            generateOutputSchema = true,
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = true,
                    destructiveHint = false,
                    idempotentHint = true))
    public OperationDto getOperation(
            @McpToolParam(description = "UUID of the recorded operation", required = true) String operationId) {
        UUID id;
        try {
            id = UUID.fromString(operationId);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("operationId must be a valid UUID", exception);
        }

        OperationDto operation = operationService.getOperationById(id);
        if (operation == null) {
            throw new IllegalArgumentException("No operation found for id " + id);
        }
        return operation;
    }
}
