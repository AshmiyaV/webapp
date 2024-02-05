package com.neu.csye6225.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.neu.csye6225.service.ConnectionCheckerService;

@RestController
@RequestMapping("/healthz")
public class ConnectionCheckerController {
	ConnectionCheckerService connectioncheckerService;

	@Autowired
	public ConnectionCheckerController(ConnectionCheckerService connectioncheckerService) {
		super();
		this.connectioncheckerService = connectioncheckerService;
	}

	@GetMapping
	public ResponseEntity<String> checkDatabaseConnection(@RequestParam Map<String, String> requestParam, @RequestBody(required = false) String requestBody, HttpMethod httpMethod){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setCacheControl(CacheControl.noCache().mustRevalidate());
		httpHeaders.setPragma("no-cache");
		httpHeaders.add("X-Content-Type-Options", "nosniff");
		try {
			if((requestParam != null && !requestParam.isEmpty()) || (requestBody != null && !requestBody.isEmpty())) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.headers(httpHeaders)
						.build();
			}
		connectioncheckerService.checkDatabaseConnection();
		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(httpHeaders)
				.build();
		} catch(RuntimeException e) {
			return ResponseEntity
					.status(HttpStatus.SERVICE_UNAVAILABLE)
					.headers(httpHeaders)
					.build();
		}
	}

	@RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.HEAD})
	public ResponseEntity<Void> handleCheckDatabaseHeadAndOptionsMethod() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setCacheControl(CacheControl.noCache().mustRevalidate());
		httpHeaders.setPragma("no-cache");
		httpHeaders.add("X-Content-Type-Options", "nosniff");
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.headers(httpHeaders)
				.build();
	}
}
