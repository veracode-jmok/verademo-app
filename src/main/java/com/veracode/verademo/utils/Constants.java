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
	 * 
	 * For running in an AWS Elastic Beanstalk, the variables are set in EBS environments.
	 * For running in a Docker image, the Tomcat's setenv.sh is used
	 */
	public Constants() {
		String dbnameProp = System.getProperty("RDS_DB_NAME");
		this.dbname = (dbnameProp == null) ? JDBC_DATABASE : dbnameProp;
		logger.info("DB_NAME: " + this.dbname);
		
		String hostnameProp = System.getProperty("RDS_HOSTNAME");
		this.hostname = (hostnameProp == null) ? JDBC_HOSTNAME : hostnameProp;
		logger.info("HOSTNAME: " + this.hostname);

		String portProp = System.getProperty("RDS_PORT");
		this.port = (portProp == null) ? JDBC_PORT : portProp;
		logger.info("PORT: " + this.port);

		String userProp = System.getProperty("RDS_USERNAME");
		this.username = (userProp == null) ? JDBC_USER : userProp;
		logger.info("USERNAME: " + this.username);

		String passwordProp = System.getProperty("RDS_PASSWORD");
		this.password = (passwordProp == null) ? JDBC_PASSWORD : passwordProp;
		logger.info("PASSWORD: " + this.password);
	}

	public static final Constants create() {
		return new Constants();
	}

	public final String getJdbcConnectionString() {
		String connection = null;
		try {
			connection = String.format(
					"jdbc:%s://%s:%s/%s?user=%s&password=%s&allowPublicKeyRetrieval=true&useSSL=false",
					JDBC_DRIVER,
					hostname,
					port,
					dbname,
					URLEncoder.encode(username, "UTF-8"),
					URLEncoder.encode(password, "UTF-8")
			);

			logger.info("JDBC Conn String: " + connection);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
