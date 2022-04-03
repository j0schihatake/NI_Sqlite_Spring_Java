package com.j0schi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(ServerApplication.class, args);
	}
}
