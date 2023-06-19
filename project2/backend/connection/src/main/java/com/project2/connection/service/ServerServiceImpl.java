package com.project2.connection.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project2.connection.model.Server;
import com.project2.connection.repository.ServerRepo;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project2.connection.enumeration.Status;


@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
	
	@Autowired
	private ServerRepo serverRepo;
	
	private Logger log = LoggerFactory.getLogger("SampleLogger");


	public Server create(Server server) { //POST Request
		log.info("Saving new server: {}", server.getName());
		
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);
	}

	private String setServerImageUrl() {
		String[] imageNames = { "server1.jpeg", "server2.jpeg", "server3.jpeg", "server4.jpeg" };
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + 
				imageNames[new Random().nextInt(4) ]).toUriString();
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all servers.");
		
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching a server by id: {}", id);
		
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating server: {}", server.getName());
		
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server by ID: {}", id);
		
		serverRepo.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server IP: {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		
		serverRepo.save(server);
		
		return server;
	}

}