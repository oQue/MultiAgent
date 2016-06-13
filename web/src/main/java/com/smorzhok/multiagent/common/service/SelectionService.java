package com.smorzhok.multiagent.common.service;

import com.smorzhok.multiagent.common.entity.DataObject;

import java.util.List;

/**
 * Service layer for data access
 *
 * @author Dmitry Smorzhok
 */
public interface SelectionService {

    /**
     * Saves given object in database
     * @param object object to save
     */
    <T extends DataObject> void save(T object);

    /**
     * Deletes given object from database
     * @param object object to save
     */
    <T extends DataObject> void delete(T object);

    /**
     * Deletes object of given class by id
     * @param clazz class of object
     * @param id    id of object to delete
     */
    <T extends DataObject> void deleteById(Class<T> clazz, long id);

    /**
     * Get object of type T from database by id
     * @param id    entity id
     * @return      all objects of given type T from db
     */
    <T extends DataObject> T getObjectById(Class<T> clazz, long id);

    /**
     * Get all objects of type T from database
     * @param clazz class of objects
     * @return      all objects of given type T from db
     */
    <T extends DataObject> List<T> getAll(Class<T> clazz);

    /**
     * Gets objects from db for pagination
     * @param clazz     class of object
     * @param page      number of page
     * @param pageSize  size of single page
     * @return          list of objects for given page
     */
    <T extends DataObject> List<T> listObjectByPage(Class<T> clazz, int page, int pageSize);

    /**
     * Counts total number of records in database
     * @param clazz class of object to count
     * @return      amount of records
     */
    <T extends DataObject> long countAll(Class<T> clazz);

}
