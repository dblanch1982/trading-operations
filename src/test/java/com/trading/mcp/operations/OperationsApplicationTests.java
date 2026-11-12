package com.trading.mcp.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.trading.mcp.operations.model.OrderSide;
import com.trading.mcp.operations.model.TradingOperation;
import com.trading.mcp.operations.repository.OperationRepository;

@SpringBootTest
class OperationsApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private OperationRepository operationRepository;

	@Test
	@Transactional
	void startsAndRendersPersistedOperationsOnOverviewPage() throws Exception {
		operationRepository.saveAndFlush(new TradingOperation(
				UUID.randomUUID(),
				"AAPL",
				OrderSide.BUY,
				2.0,
				150.0,
				Instant.parse("2026-01-01T10:00:00Z")));

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("AAPL"));
	}

}
