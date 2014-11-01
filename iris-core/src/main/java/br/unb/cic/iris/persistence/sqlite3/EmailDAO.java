/*
 * EmailDAO.java
 * ---------------------------------
 *  version: 0.0.1
 *  date: Sep 18, 2014
 *  author: rbonifacio
 *  list of changes: (none) 
 */
package br.unb.cic.iris.persistence.sqlite3;

import java.util.logging.Logger;

import br.unb.cic.iris.core.exception.DBException;
import br.unb.cic.iris.core.model.EmailMessage;
import br.unb.cic.iris.persistence.IEmailDAO;

/**
 * An implementation of @see br.unb.cic.iris.persistence.EmailDAO using the
 * SQLite databaese.
 * 
 * @author rbonifacio
 *
 */
public class EmailDAO extends AbstractDAO<EmailMessage> implements IEmailDAO {

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
		super.saveOrUpdate(message);
		/*Session session = null;
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
		}*/
	}


}
