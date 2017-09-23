package ua.com.studyazimut.DAO.repository;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import ua.com.studyazimut.DAO.DAOException;

import java.util.Collections;
import java.util.List;

public abstract class AbstractDAO {
    private Session session;

    private Transaction transaction;

    public List findAllRecords(Class clazz) throws DAOException {
        List result = Collections.emptyList();
        try {
            beginTransaction();
            Criteria cr = session.createCriteria(clazz);
            result = cr.list();
            commitTransaction();
        } catch (HibernateException e) {
            transactionRollback();
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeSession();
        }
        return result;
    }

    public void saveRecord(Object record) throws DAOException {
        try {
            beginTransaction();
            session.save(record);
            commitTransaction();
        } catch (HibernateException e) {
            transactionRollback();
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeSession();
        }
    }

    public void updateRecord(Object record) throws DAOException {
        try {
            beginTransaction();
            session.update(record);
            commitTransaction();
        } catch (Exception e) {
            transactionRollback();
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeSession();
        }
    }

    public Object findById(Class clazz, int id) throws DAOException {
        Object result = null;
        try {
            beginTransaction();
            result = session.load(clazz, id);
            Hibernate.initialize(result);
            commitTransaction();
        } catch (Exception e) {
            transactionRollback();
            result = new Object();
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeSession();
        }
        return result;
    }

    public List findBy(Class clazz, String columnName, Object columnValue) throws DAOException {
        List result = Collections.emptyList();
        try {
            beginTransaction();
            Criteria criteria = session.createCriteria(clazz);
            criteria.add(Restrictions.eq(columnName, columnValue));
            result = criteria.list();
            commitTransaction();
        } catch (Exception e) {
            transactionRollback();
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeSession();
        }
        return result;
    }

    // +++++++++++ cleanTable() FOR TESTS ONLY  ++++++++++
    public void cleanTable(String tableName) {
        try {
            beginTransaction();
            String sql = String.format("delete from %s", tableName);
            Query query = session.createQuery(sql);
            query.executeUpdate();
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession();
        }
    }

    private void beginTransaction() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private void commitTransaction() {
        transaction.commit();
    }

    private void transactionRollback() {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    private void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
