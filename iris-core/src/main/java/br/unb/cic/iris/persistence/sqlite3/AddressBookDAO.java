package br.unb.cic.iris.persistence.sqlite3;

import java.util.List;

import org.hibernate.Session;

import br.unb.cic.iris.core.exception.DBException;
import br.unb.cic.iris.core.model.AddressBookEntry;
import br.unb.cic.iris.persistence.IAddressBookDAO;
import br.unb.cic.iris.util.HibernateUtil;

public class AddressBookDAO implements IAddressBookDAO {
		
	private static final String FIND_BY_NICK_NAME = 
			"FROM AddressBookEntry a "
			+ "where a.nick = :pNick";
	
	@Override
	public void save(AddressBookEntry entry) throws DBException{
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(entry);
		}
		catch(Exception e) {
			throw new DBException("could not save the address book entry", e);
		}
		finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	
	@Override
	public AddressBookEntry find(String nick) throws DBException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			List<AddressBookEntry> entries = session.createQuery(FIND_BY_NICK_NAME).setParameter("pNick", nick).list();
			
			if(entries != null && entries.size() == 1) {
				return entries.get(0);
			}
			return null;
		}
		catch(Exception e) {
			throw new DBException("could not save the address book entry", e);	
		}
		finally {
			session.close();
		}
	}

	@Override
	public void delete(String nick) {
		
	}

}
