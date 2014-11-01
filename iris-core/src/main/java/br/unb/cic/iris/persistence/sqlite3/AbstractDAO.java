package br.unb.cic.iris.persistence.sqlite3;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;

import br.unb.cic.iris.core.exception.EmailUncheckedException;
import br.unb.cic.iris.util.HibernateUtil;

public abstract class AbstractDAO<T> {
	private Class clazz;
	private Session session;
	private Transaction tx;
	
	public AbstractDAO(){
		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void setSession(SessionFactory sf){
		session = sf.openSession();
	}
	
	protected void saveOrUpdate(T obj) {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
        	closeSession();
        }
    }
	
	public void delete(T t){
		try {
            startOperation();
            session.delete(t);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
        	closeSession();
        }
	}
	
	public T findById(Long id) {
        T obj = null;
        try {
            startOperation();
            obj = (T) session.load(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
        	closeSession();
        }
        return obj;
    }

    public List<T> findAll() {
        List<T> objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
        	closeSession();
        }
        return objects;
    }

	public List<T> findByExample(T filtro, MatchMode matchMode, boolean ignoreCase){
		org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(filtro);

		if(matchMode != null){
			example = example.enableLike(matchMode);
		}

		if(ignoreCase){
			example = example.ignoreCase();
		}

		return session.createCriteria(clazz).add(example).list();
	}
	
	
	protected void handleException(HibernateException e) throws EmailUncheckedException {
        tx.rollback();
        throw new EmailUncheckedException(e.getMessage(), e);
    }

    protected void startOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }
    
	private void closeSession() {
		if(session != null && session.isOpen()) {
			session.close();
		}
	}
}
