package com.revature;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.daos.BearDao;
import com.revature.daos.CaveDao;
import com.revature.models.Bear;
import com.revature.models.Cave;


/**
 * CRUD methods to know on the Session object
 * 1. save
 * 2. persist
 * 3. get
 * 4. load
 * 5. update
 * 6. merge
 * 7. delete
 * 
 * * Updating with automatic dirty checking
 * * saveOrUpdate
 *
 */
public class Launcher {
	private static SessionFactory sf;
	
	private static CaveDao caveDao;
	private static BearDao bearDao;
	
	public static SessionFactory getSessionFactory() {
		return sf;
	}
	
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
				.addAnnotatedClass(Bear.class)
				.addAnnotatedClass(Cave.class);
		
		// Use configuration to create serviceRegistry
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
		
		// Use configuration to create sessionFactory, passing in serviceRegistry
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
	
	
	
	static void runCrudDemo() {
		BearDao bearDao = new BearDao();

		Optional<Bear> bear = bearDao.selectBear(2);
		if(bear.isPresent()) {
			System.out.println(bear);
		} else {
			System.out.println("No bear with the provided id");
		}
		
		
		System.out.println("Loading bear lazily");
		Bear loadedBear = bearDao.loadBear(1);
		System.out.println(loadedBear);
		
		LocalDate birthDate = loadedBear.getDateOfBirth();
		LocalDate modifiedDate = birthDate.minus(1, ChronoUnit.DAYS);
		loadedBear.setDateOfBirth(modifiedDate);
		bearDao.updateBear(loadedBear);
		
		Bear brandNewBear = new Bear(0, "male", "Grizzly Bear", LocalDate.now(), 100.0);
		bearDao.insertBear(brandNewBear);
		bearDao.deleteBear(brandNewBear);
	}
	
	public static void relationalDemo() {
		BearDao bearDao = new BearDao();
		CaveDao caveDao = new CaveDao();
		
//		Cave cave = new Cave(0, "Bear Cave", "Underground", 500);
//		caveDao.insertCave(cave);
//		Bear bear = bearDao.loadBear(1);
//		caveDao.addBear(cave, bear);
		
//		Optional<Cave> cave = caveDao.getCave(4);
//		logger.warn(cave.get());
		
//		List<Bear> bears = bearDao.getBearsByCaveId(4);
//		System.out.println(bears);
		
//		Bear bear = bearDao.selectBear(1).get();
//		logger.warn(bear);
//		logger.warn(bear.getCave());

		// This will work because we have configured Cave to cascade persist operations
		// to their bears
//		List<Bear> bears = new ArrayList<>();
//		
//		bears.add(new Bear(0, "female", "Panda", LocalDate.now(), 1000));
//		bears.add(new Bear(0, "female", "Brown Bear", LocalDate.now(), 1000));
//		
//		Cave cave = new Cave(0, "Luminous Cave", "Canada", 10000, bears);
//		caveDao.insertCave(cave);
		
		
		Bear bear = bearDao.loadBear(1);
		Bear cubA = bearDao.loadBear(5);
		Bear cubB = bearDao.loadBear(6);
		
		bearDao.addCubs(bear, cubA, cubB);		
	}
	
	public static void main(String[] args) {
		sf = configureHibernate();

		try {
			relationalDemo();
		} catch(PersistenceException e) {
			e.printStackTrace();
			sf.close();
		}
		
	}
}
