package io.fouad.bugs.bug01.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import io.fouad.bugs.bug01.utils.RequiredBy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Profile("development")
@Slf4j
@Component
@RequiredBy("dataSource")
public class EmbeddedMssqlServer {
	
	private static final String DOCKER_IMAGE_NAME = "mcr.microsoft.com/mssql/server:2022-latest";
	
	private boolean started = false;
	
	private Class<?> mssqlServerClass;
	private Object mssqlServer;
	
	@SuppressWarnings("JavaReflectionInvocation")
	@PostConstruct
	public void startMssql() {
		try {
			mssqlServerClass = Class.forName("org.testcontainers.containers.MSSQLServerContainer");
			mssqlServer = mssqlServerClass.getConstructor(String.class).newInstance(DOCKER_IMAGE_NAME);
			mssqlServerClass.getMethod("acceptLicense").invoke(mssqlServer);
			mssqlServerClass.getMethod("addEnv", String.class, String.class)
							.invoke(mssqlServer, "MSSQL_PID", "Express"); // Developer, Express, Standard, Enterprise, or EnterpriseCore
			mssqlServerClass.getMethod("start").invoke(mssqlServer);
			String username = (String) mssqlServerClass.getMethod("getUsername").invoke(mssqlServer);
			String password = (String) mssqlServerClass.getMethod("getPassword").invoke(mssqlServer);
			String url = (String) mssqlServerClass.getMethod("getJdbcUrl").invoke(mssqlServer);
			url += ";databaseName=%s;".formatted("mydb");
			log.info("MSSQL JDBC URL: " + url);
			System.setProperty("spring.datasource.url", url);
			System.setProperty("spring.datasource.username", username);
			System.setProperty("spring.datasource.password", password);
			started = true;
			log.info("Started MSSQL dev server successfully!");
		} catch (Throwable t) {
			log.warn("Failed to start MSSQL dev server!", t);
			return;
		}
		
		try {
			var scriptUtilsClass = Class.forName("org.testcontainers.ext.ScriptUtils");
			var databaseDelegateClass = Class.forName("org.testcontainers.delegate.DatabaseDelegate");
			var jdbcDatabaseDelegateClass = Class.forName("org.testcontainers.jdbc.JdbcDatabaseDelegate");
			var jdbcDatabaseContainerClass = Class.forName("org.testcontainers.containers.JdbcDatabaseContainer");
			var jdbcDatabaseDelegate = jdbcDatabaseDelegateClass.getConstructor(jdbcDatabaseContainerClass, String.class).newInstance(mssqlServer, "");
			var runInitScriptMethod = scriptUtilsClass.getMethod("runInitScript", databaseDelegateClass, String.class);
			
			runInitScriptMethod.invoke(null, jdbcDatabaseDelegate, "sql/ddl.sql");
			log.info("Executed ddl.sql successfully!");
			
			runInitScriptMethod.invoke(null, jdbcDatabaseDelegate, "sql/dml.sql");
			log.info("Executed dml.sql successfully!");
		} catch (Throwable t) {
			log.warn("Failed to execute initial MSSQL dev scripts!", t);
		}
	}
	
	@PreDestroy
	public void stopMssql() {
		if (!started) {
			return;
		}
		
		try {
			mssqlServerClass.getMethod("stop").invoke(mssqlServer);
			log.info("Stopped MSSQL dev server successfully!");
		} catch (Throwable e) {
			log.warn("Failed to stop the MSSQL dev server!");
		}
	}
}