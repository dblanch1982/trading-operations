package com.trading.mcp.operations.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trading.mcp.operations.model.TradingOperation;

public interface OperationRepository extends JpaRepository<TradingOperation, UUID> {

}