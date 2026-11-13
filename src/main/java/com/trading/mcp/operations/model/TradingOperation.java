package com.trading.mcp.operations.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "trading_operations")
public class TradingOperation {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = false)
    private OrderSide side;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public UUID getId() {
        return id;
    }

    public String getSymbol() { 
        return symbol;
    }

    public OrderSide getSide() {
        return side;
    }   
    public double getQuantity() {
        return quantity;
    }       
    public double getPrice() {  
        return price;
    } 
    public Instant getTimestamp() {
        return timestamp;
    }

}
