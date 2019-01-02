package cn.mauth.ccrm.core.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 仅暴露Dbunit对Excel的支持，是因为几种数据提供方案中，Excel编辑数据最容易，在实践中被使用的最广泛。 支持每次Setup是否清空数据的选择
 */
public class DatabaseUnitHelper {

	public static final String DEFAULT_CONFIG_FILE = "db.properties";

	public static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";

	public static final String DEFAULT_PASS = "root";

	public static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/mauths?useUnicode=true&characterEncoding=UTF-8";

	public static final String DEFAULT_USER = "root";

	public static final String DRIVER = "connection.driver_class";

	public static final String PASS = "connection.password";

	public static final String URL = "connection.url";

	public static final String USER = "connection.username";



	private static Properties config = new Properties();
 
	private static final Log log = LogFactory.getLog(DatabaseUnitHelper.class);


	private String connectionPassword;

	private String connectionUrl;

	private String connectionUser;

	private String driverClazz;


	public DatabaseUnitHelper() {
		doInit(DEFAULT_CONFIG_FILE);
	}
	
	public void doInit(String path) {
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			log.error("could not load the properties file : " + path, e);
		}
		Properties properties = new Properties();
		properties.setProperty(DRIVER, config.getProperty(DRIVER, DEFAULT_DRIVER));
		properties.setProperty(USER, config.getProperty(USER, DEFAULT_USER));
		properties.setProperty(PASS, config.getProperty(PASS, DEFAULT_PASS));
		properties.setProperty(URL, config.getProperty(URL, DEFAULT_URL));
		doInit(properties);
	}
	
	public void doInit(Properties properties) {
		if (properties == null || properties.isEmpty()) {
			return;
		}
		this.driverClazz = properties.getProperty(DRIVER);
		this.connectionUrl = properties.getProperty(URL);
		this.connectionUser = properties.getProperty(USER);
		this.connectionPassword = properties.getProperty(PASS);
	}
	/**
	 * Close the specified connection. Ovverride this method of you want to keep your connection alive between tests.
	 */
	protected void closeConnection(Connection connection) throws Exception {
		connection.close();
	}


	public Connection getJdbcConnection() throws Exception {
		return this.getConnection();
	}
	
	/**
	 * Returns the test database connection.
	 */
	public Connection getConnection() throws Exception {
		Class.forName(this.driverClazz);
		return DriverManager.getConnection(this.connectionUrl, this.connectionUser,
				this.connectionPassword);
	}
	
	public String getDriver() {
		return this.driverClazz;
	}
	
	public String getConnectionUrl() {
		return this.connectionUrl;
	}
	public String getPassword() {
		return this.connectionPassword;
	}
	public String getUser() {
		return this.connectionUser;
	}

}
