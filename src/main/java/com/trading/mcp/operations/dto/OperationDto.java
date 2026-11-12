package com.trading.mcp.operations.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;   
import lombok.NoArgsConstructor;

import com.trading.mcp.operations.model.OrderSide;

@Getter
@Setter
@NoArgsConstructor
public class OperationDto {
    private UUID id;
    private String symbol;
    private OrderSide side;
    private double quantity;
    private double price;
    private Instant timestamp;

    public OperationDto(UUID id,
            String symbol,
            OrderSide side,
            double quantity,
            double price,
            Instant timestamp) {
        this.id = id;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

}