package com.project2.connection.resource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project2.connection.enumeration.Status;
import com.project2.connection.model.Response;
import com.project2.connection.model.Server;
import com.project2.connection.service.ServerServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
	
	@Autowired
	private ServerServiceImpl serverService;
	
	@GetMapping("/list")
	public ResponseEntity<Response> getServers() {
		return ResponseEntity.ok(
				new Response(LocalDateTime.now(), 
						HttpStatus.OK.value(), 
						HttpStatus.OK, 
						"", 
						"Servers retrieved", 
						"", 
						Map.of("servers", serverService.list(20)))
		);
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> getPingServer(
			@PathVariable("ipAddress") String ipAddress) throws IOException {
		
		Server server = serverService.ping(ipAddress);
		
		return ResponseEntity.ok(
				new Response(LocalDateTime.now(), 
						HttpStatus.OK.value(), 
						HttpStatus.OK, 
						"", 
						server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed", 
						"", 
						Map.of("server", server))
		);
	}

}