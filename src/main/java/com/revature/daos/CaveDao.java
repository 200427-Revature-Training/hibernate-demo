package com.revature.daos;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.Launcher;
import com.revature.models.Bear;
import com.revature.models.Cave;

public class CaveDao {

	SessionFactory sf;
	Logger logger = Logger.getRootLogger();
	
	public CaveDao() {
		super();
		sf = Launcher.getSessionFactory();
	}

	public void insertCave(Cave cave) {
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			session.persist(cave);
			tx.commit();
		}
	}
	
	public void addBear(Cave cave, Bear bear) {
		if(cave.getBears() == null) {
			cave.setBears(new ArrayList<Bear>());
		}
		
		if(cave.getBears().contains(bear)) {
			logger.warn("Bear already in cave!");
			return;
		}
		
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			cave.getBears().add(bear);
			cave = (Cave) session.merge(cave);
			tx.commit();
		}
	}
	
	public Optional<Cave> getCave(int id) {
		try(Session session = sf.openSession()) {
			Cave cave = session.get(Cave.class, id);
			
			if (cave == null) return Optional.empty();
			
			Hibernate.initialize(cave.getBears());
			return Optional.of(cave);
		}
	}
	
	public Cave updateCave(Cave cave) {
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			cave = (Cave) session.merge(cave);
			tx.commit();
			return cave;
		}
	}
	
	public void deleteCave(Cave cave) {
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			session.delete(cave);
			tx.commit();
		}
	}
	
}
