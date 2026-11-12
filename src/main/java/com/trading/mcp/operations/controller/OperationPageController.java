package com.trading.mcp.operations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trading.mcp.operations.service.OperationService;

@Controller
public class OperationPageController {

    private final OperationService operationService;

    public OperationPageController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/")
    public String getOperationsPage(Model model) {
        model.addAttribute("operations", operationService.getAllOperations());
        return "index";
    }
}
