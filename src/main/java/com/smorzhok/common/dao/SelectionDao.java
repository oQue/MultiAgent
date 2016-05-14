package com.smorzhok.common.dao;

import com.smorzhok.common.entity.DataObject;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * Common interface for dao implementations
 *
 * @author Dmitry Smorzhok
 */
public interface SelectionDao {

    /**
     * saves given object in database
     * @param object - object to save
     */
    <T extends DataObject> void save(T object);

    /**
     * deletes given object in database
     * @param object - object to save
     */
    <T extends DataObject> void delete(T object);

    /**
     * deletes object of given class by id
     * @param clazz - class of objects
     * @param id - id of object to delete
     */
    <T extends DataObject> void deleteById(Class<T> clazz, Long id);

    /**
     * get all objects of type T from database
     * @param clazz - class of objects
     * @return all objects of given type T from db
     */
    <T extends DataObject> List<T> getAll(Class<T> clazz);

    /**
     * get all objects of type T from database by given query
     * @param query - DetachedCriteria query
     * @return all objects of given type T from db
     */
    <T extends DataObject> List<T> get(DetachedCriteria query);

    /**
     * get object of type T from database by query
     * @param query - DetachedCriteria query
     * @return object by given qury
     */
    <T extends DataObject> T getObjectByQuery(DetachedCriteria query);

    /**
     * gets objects from db for pagination
     * @param clazz - class of object
     * @param page - number of page
     * @param pageSize - size of single page
     * @return list of objects for given page
     */
    <T extends DataObject> List<T> listObjectByPage(Class<T> clazz, int page, int pageSize);

    /**
     * gets objects from db for pagination by given criteria
     * @param query - DetachedCriteria query
     * @param page - number of page
     * @param pageSize - size of single page
     * @return list of objects for given page
     */
    <T extends DataObject> List<T> listObjectByPage(DetachedCriteria query, int page, int pageSize);

    /**
     * counts total number of records in database
     * @param clazz - class of object to count
     * @return amount of records
     */
    <T extends DataObject> long countAll(Class<T> clazz);

    /**
     * counts total number of records in database with given query
     * @param query - DetachedCriteria query
     * @return amount of records
     */
    <T extends DataObject> long count(DetachedCriteria query);

}
