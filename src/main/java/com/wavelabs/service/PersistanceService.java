package com.wavelabs.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wavelabs.model.Television;
import com.wavelabs.utility.Helper;

/**
 * This class provides static method to perform CRUD operations
 * {@link Television} entity.
 * 
 * @author gopikrishnag
 * 
 */
public class PersistanceService {
	/**
	 * <h3>Persist Television object with given properties</h3>
	 * <ul>
	 * <li>Creates a television object</li>
	 * <li>sets values to television</li>
	 * <li>persist television object
	 * <li>
	 * </ul>
	 * @param id
	 *            of Television
	 * @param name
	 *            of Television
	 * @param cost
	 *            of Television
	 * @param warranty
	 *            of Television
	 */
	public static void createTelevision(int id, String name, int cost, int warranty) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Television t1 = new Television();
		t1.setId(id);
		t1.setName(name);
		t1.setCost(cost);
		t1.setWarranty(warranty);
		session.save(t1);
		tx.commit();
		session.close();
	}

	/**
	 * <h3>updates warranty, cost properties based on matching id</h3>
	 * <ul>
	 * <li>load the persisted object of given id
	 * </li>
	 * <li>set new properties to persist object</li>
	 * <li>update the persist object</li>
	 * </ul>
	 * 
	 * @param id
	 *            of Television
	 * @param warranty
	 *            of Television
	 * @param cost
	 *            of Television
	 */
	public static void updateTelevisonProperties(int id, Integer warranty, double cost) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Television t1 = (Television) session.get(Television.class, id);
		t1.setWarranty(warranty);
		t1.setCost(cost);
		session.update(t1);
		session.flush();
		tx.commit();
		session.close();
	}
}
