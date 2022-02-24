package com.aelion.simpleorm.dbal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.aelion.simpleorm.utils.PropertiesUtil;

public abstract class DbConnect {
	protected String dsn;
	
	protected String userName;
	protected String userPassword;
	
	public abstract Connection connect() throws SQLException;
	
	public abstract void disconnect() throws SQLException;
	
	protected Properties readProperties() {
		Properties properties = new PropertiesUtil().loadProperties();
		
		this.userName = properties.getProperty("DB_USER");
		this.userPassword = properties.getProperty("DB_PASSWORD");
		
		return properties;
	}
}
