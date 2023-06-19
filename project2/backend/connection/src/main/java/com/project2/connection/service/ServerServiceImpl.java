package com.project2.connection.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		server.setImageUrl(setServerImageUrl(""));
		return serverRepo.save(server);
	}

	private String setServerImageUrl(String ipAddress) {
		log.info("Saving server IP: {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		
		return null;
	}

	@Override
	public Collection<Server> list(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server update(Server server) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		return null;
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