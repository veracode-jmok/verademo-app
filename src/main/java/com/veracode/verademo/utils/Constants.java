package com.veracode.verademo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Constants {
	private static final Logger logger = LogManager.getLogger("VeraDemo:Constants");

	private final String JDBC_DRIVER = "mysql";
	private final String JDBC_HOSTNAME = "localhost";
	private final String JDBC_PORT = "3306";
	private final String JDBC_DATABASE = "blab";
	private final String JDBC_USER = "blab";
	/* START BAD CODE */
	private final String JDBC_PASSWORD = "z2^E6J4$;u;d";
	/* END BAD CODE */

	private String hostname;
	private String port;
	private String dbname;
	private String username;
	private String password;

	/**
	 * Pull info from the system as an override, otherwise fall back to hardcoded values.
	 * Environment variables are automatically set in AWS environments.
	 * 	(kfr: The Elastic Beanstalk passes these as Env Props, while normal Tomcat does not,
	 * 		so use Env Vars which can be passed as part of Docker startup)
	 */
	public Constants() {
		//String dbnameProp = System.getProperty("RDS_DB_NAME");
		String dbnameProp = System.getenv("RDS_DB_NAME");
		this.dbname = (dbnameProp == null) ? JDBC_DATABASE : dbnameProp;
		logger.info("DB_NAME: " + this.dbname);
		
		//String hostnameProp = System.getProperty("RDS_HOSTNAME");
		String hostnameProp = System.getenv("RDS_HOSTNAME");
		this.hostname = (hostnameProp == null) ? JDBC_HOSTNAME : hostnameProp;
		logger.info("HOSTNAME: " + this.hostname);

		//String portProp = System.getProperty("RDS_PORT");
		String portProp = System.getenv("RDS_PORT");
		this.port = (portProp == null) ? JDBC_PORT : portProp;
		logger.info("PORT: " + this.port);

		//String userProp = System.getProperty("RDS_USERNAME");
		String userProp = System.getenv("RDS_USERNAME");
		this.username = (userProp == null) ? JDBC_USER : userProp;
		logger.info("USERNAME: " + this.username);

		//String passwordProp = System.getProperty("RDS_PASSWORD");
		String passwordProp = System.getenv("RDS_PASSWORD");
		this.password = (passwordProp == null) ? JDBC_PASSWORD : passwordProp;
		logger.info("PASSWORD: you're kidding, right...");
	}

	public static final Constants create() {
		return new Constants();
	}

	public final String getJdbcConnectionString() {
		String connection = null;
		try {
			connection = String.format(
					"jdbc:%s://%s:%s/%s?user=%s&password=%s",
					JDBC_DRIVER,
					hostname,
					port,
					dbname,
					URLEncoder.encode(username, "UTF-8"),
					URLEncoder.encode(password, "UTF-8")
			);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
