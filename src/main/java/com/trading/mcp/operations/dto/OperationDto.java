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
@AllArgsConstructor 
public class OperationDto {
    private UUID id;
    private String symbol;
    private OrderSide side;
    private double quantity;
    private double price;
    private Instant timestamp;

}