package com.revature;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.models.Bear;

public class Launcher {
	static SessionFactory sf;
	static Logger logger = Logger.getRootLogger();
	/**
	 * Loads and augments Hibernate configuration, defines entities, and returns a configured
	 * SessionFactory
	 */
	static SessionFactory configureHibernate() {
		// Create jdbc url from database URL
		String jdbcUrl = String.format("jdbc:postgresql://%s:5432/postgres", 
				System.getenv("NODE_APP_URL"));

		// Create configuration instance
		Configuration configuration = new Configuration()
				.configure()
				.setProperty("hibernate.connection.username", System.getenv("NODE_APP_ROLE"))
				.setProperty("hibernate.connection.url", jdbcUrl)
				.setProperty("hibernate.connection.password", System.getenv("NODE_APP_PASS"))
				.addAnnotatedClass(Bear.class);
		
		// Use configuration to create serviceRegistry
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
		
		// Use configuration to create sessionFactory, passing in serviceRegistry
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
	
	public static void main(String[] args) {
		sf = configureHibernate();

		// Hibernate assumes 0 id means new object
		Bear bear = new Bear(0, "male", "Polar", LocalDate.of(2020, 6, 16), 20.0);
		
		// A session is a borrowed connection from the pool
		// Session implements AutoClosable - We can use it in a try-with-resources block
		// When the try block is concluded, the session will automatically close
		try(Session session = sf.openSession()) {
			session.save(bear);
		}
		
		
		
		
		
		
		
		
		
		sf.close();
	}
}
