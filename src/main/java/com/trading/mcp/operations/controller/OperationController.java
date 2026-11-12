package com.trading.mcp.operations.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trading.mcp.operations.dto.OperationDto;
import com.trading.mcp.operations.model.TradingOperation;
import com.trading.mcp.operations.service.OperationService;

@Controller
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }


    @GetMapping("/api/operations")
    @ResponseBody
    public List<OperationDto> getOperationsApi() {
        return operationService.getAllOperations();
    }

}
