package com.project2.connection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project2.connection.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {
	
	Server findByIpAddress(String ipAddress);
}