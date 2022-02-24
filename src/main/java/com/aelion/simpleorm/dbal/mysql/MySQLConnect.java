package com.aelion.simpleorm.dbal.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.aelion.simpleorm.dbal.DbConnect;

public class MySQLConnect extends DbConnect {

	private static MySQLConnect instance;
	
	private Connection connection;
	
	private MySQLConnect() {
		Properties properties = this.readProperties();
		
		StringBuilder sb = new StringBuilder();
		sb
			.append("jdbc:")
			.append("mysql://")
			.append(properties.getProperty("DB_HOST"))
			.append(":")
			.append(properties.getProperty("DB_PORT"))
			.append("/")
			.append(properties.getProperty("DB_NAME"));
		
		this.dsn = sb.toString();
	}
	
	public static MySQLConnect getInstance() {
		if (MySQLConnect.instance == null) {
			MySQLConnect.instance = new MySQLConnect();
		}
		
		return MySQLConnect.instance;
	}
	
	@Override
	public Connection connect() throws SQLException {
		this.connection = DriverManager.getConnection(this.dsn, this.userName, this.userPassword);
	
		return this.connection;
	}
	
	@Override
	public void disconnect() throws SQLException {
		this.connection.close();
	}

}
