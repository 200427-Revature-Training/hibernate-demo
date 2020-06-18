package com.revature.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.Launcher;
import com.revature.models.Bear;
import com.revature.models.Cave;

public class BearDao {
	
	SessionFactory sf;
	
	public BearDao() {
		super();
		sf = Launcher.getSessionFactory();
	}

	/**
	 * Creating a new Record
	 * save - saves a "transient" instance, returns a Serializable ID
	 * persist - JPA method, makes "transient" instance "persistent"
	 * @param bear
	 */
	public void insertBear(Bear bear) {
		// A session is a borrowed connection from the pool
		// Session implements AutoClosable - We can use it in a try-with-resources block
		// When the try block is concluded, the session will automatically close
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			session.persist(bear);
			// Automatic Dirty Checking - Changes on an object will automatically be pushed upon
			// commit if changed on a "persistent" object
			bear.setBreed("Brown Bear");
			tx.commit();
		}
	}
	
	/**
	 * HQL query to retrieve bears by breed
	 * @param breed
	 * @return
	 */
	public List<Bear> getBearsByBreed(String breed) {
		try(Session session = sf.openSession()) {
			String hql = "FROM Bear b WHERE LOWER(b.breed) = :breed";
			Query<Bear> query = session.createQuery(hql, Bear.class);
			query.setParameter("breed", breed.toLowerCase());
			List<Bear> bears = query.getResultList();
			return bears;
		}
	}
	
	/**
	 * Reading data from database
	 * get() - EAGER retrieval from the database - provides persistent object or null 
	 */
	public Optional<Bear> selectBear(int id) {
		try(Session session = sf.openSession()) {
			return Optional.ofNullable(session.get(Bear.class, id));
		}
	}
	
	/**
	 * Creates a proxy for an object.
	 * The proxy will initialize lazily when it is needed.
	 * The proxy can only initialize while the session that loaded is still ongoing.
	 * Will throw LazyInitializationException if we attempt to load the proxy outside of a
	 * session.
	 * Will throw ObjectNotFoundException upon initialization if an object with that ID does
	 * not exist.
	 * @param id
	 * @return
	 */
	public Bear loadBear(int id) {
		try(Session session = sf.openSession()) {
			Bear bear = session.load(Bear.class, id);
			
			// Explicitly load in a proxy
			Hibernate.initialize(bear);
			return bear;
		}
	}
	
	/*
	 * Updates a Bear
	 * update could throw NonUniqueObjectException if there is a persistent reference tracked
	 * 	by the session
	 * 
	 */
	public void updateBear(Bear bear) {
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			Bear sameBear = session.get(Bear.class, bear.getId());
//			session.update(bear);
			bear = (Bear) session.merge(bear);
			tx.commit();
		}
	}
	
	public void deleteBear(Bear bear) {
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			session.delete(bear);
			tx.commit();
		}
	}
	
	public List<Bear> getBearsByCaveId(int caveId) {
		try(Session session = sf.openSession()) {
			// Retrieve associated instance
			Cave cave = session.get(Cave.class, caveId);
			if (cave == null) return null;
			
			// Extract the list of bears
			List<Bear> bears = cave.getBears();
			
			// Initialize this collection
			Hibernate.initialize(bears);
			
			// Return
			return bears;
		}
	}
	
	public void addCubs(Bear bear, Bear... cubs) {
		try(Session session = sf.openSession()) {
			bear = session.get(Bear.class, bear.getId());
			Transaction tx = session.beginTransaction();
			
			if(bear.getCubs() == null) {
				bear.setCubs(new ArrayList<Bear>());
			}
			
			for(Bear cub : cubs) {
				bear.getCubs().add(cub);
			}
			
			session.merge(bear);
			tx.commit();
		}
	}
}
