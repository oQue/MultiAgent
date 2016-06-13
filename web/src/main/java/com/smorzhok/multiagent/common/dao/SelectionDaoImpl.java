package com.smorzhok.multiagent.common.dao;

import com.smorzhok.multiagent.common.entity.DataObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dmitry Smorzhok
 */
@Transactional
@Repository
public class SelectionDaoImpl implements SelectionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public <T extends DataObject> void save(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public <T extends DataObject> void delete(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public <T extends DataObject> void deleteById(Class<T> clazz, Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("id", id));
        T object = (T) criteria.getExecutableCriteria(session).uniqueResult();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public <T extends DataObject> T getObjectById(Class<T> clazz, long id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.add(Restrictions.idEq(id));
        return getObjectByQuery(criteria);
    }

    @Override
    public <T extends DataObject> List<T> getAll(Class<T> clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        return get(criteria);
    }

    @Override
    public <T extends DataObject> List<T> get(DetachedCriteria query) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<T> list = query.getExecutableCriteria(session).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public <T extends DataObject> T getObjectByQuery(DetachedCriteria query) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T object = (T) query.getExecutableCriteria(session).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return object;
    }

    @Override
    public <T extends DataObject> List<T> listObjectByPage(Class<T> clazz, int page, int pageSize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        return listObjectByPage(criteria, page, pageSize);
    }

    @Override
    public <T extends DataObject> List<T> listObjectByPage(DetachedCriteria query, int page, int pageSize) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<T> list = query.getExecutableCriteria(session)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public <T extends DataObject> long countAll(Class<T> clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        return count(criteria);
    }

    @Override
    public <T extends DataObject> long count(DetachedCriteria query) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Long size = (Long) query.getExecutableCriteria(session)
                .setProjection(Projections.rowCount())
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return size;
    }

}
