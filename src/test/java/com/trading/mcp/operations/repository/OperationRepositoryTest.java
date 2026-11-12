package com.trading.mcp.operations.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.trading.mcp.operations.model.OrderSide;
import com.trading.mcp.operations.model.TradingOperation;

@DataJpaTest 
class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Test
    void persistsAndLoadsTradingOperation() {
        UUID id = UUID.randomUUID();
        TradingOperation operation = new TradingOperation(
                id,
                "AAPL",
                OrderSide.BUY,
                2.0,
                150.0,
                Instant.parse("2026-01-01T10:00:00Z"));

        operationRepository.saveAndFlush(operation);

        TradingOperation loaded = operationRepository.findById(id).orElseThrow();
        assertEquals(operation.getId(), loaded.getId());
        assertEquals(operation.getSymbol(), loaded.getSymbol());
        assertEquals(operation.getSide(), loaded.getSide());
        assertEquals(operation.getQuantity(), loaded.getQuantity());
        assertEquals(operation.getPrice(), loaded.getPrice());
        assertEquals(operation.getTimestamp(), loaded.getTimestamp());
    }
}
