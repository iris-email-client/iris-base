/*
 * EmailDAO.java
 * ---------------------------------
 *  version: 0.0.1
 *  date: Sep 18, 2014
 *  author: rbonifacio
 *  list of changes: (none) 
 */
package br.unb.cic.iris.persistence.sqlite3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import br.unb.cic.iris.core.exception.DBException;
import br.unb.cic.iris.core.model.Account;
import br.unb.cic.iris.core.model.EmailMessage;
import br.unb.cic.iris.core.model.IrisFolder;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.util.HibernateUtil;

/**
 * An implementation of @see br.unb.cic.iris.persistence.EmailDAO using the
 * SQLite databaese.
 * 
 * @author rbonifacio
 *
 */
public class EmailDAO implements IEmailDAO {

	Logger logger = Logger.getLogger(EmailDAO.class.getName());
	
	/* the single instance of EmailDAO */
	private static EmailDAO instance;

	/* private constructor, according to the singleton pattern */
	private EmailDAO() { }

	/**
	 * Retrieves the singleton instance of EmailDAO.
	 * 
	 * @return the singleton instance of EmailDAO
	 */
	public static EmailDAO instance() {
		if (instance == null) {
			instance = new EmailDAO();
		}
		return instance;
	}

	@Override
	public void saveMessage(EmailMessage message) throws DBException {
		Session session = null;
		try {
			logger.info("saving message into the database");
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(message);
		} catch (Exception e) {
			throw new DBException("could not save the sent message", e);
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}


}
