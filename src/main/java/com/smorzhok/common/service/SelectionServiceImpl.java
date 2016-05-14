package com.smorzhok.common.service;

import com.smorzhok.common.dao.SelectionDao;
import com.smorzhok.common.entity.DataObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dmitry Smorzhok
 */
@Service("selectionService")
public class SelectionServiceImpl implements SelectionService {

    @Autowired
    protected SelectionDao selectionDao;

    @Override
    public <T extends DataObject> void save(T object) {
        selectionDao.save(object);
    }

    @Override
    public <T extends DataObject> void delete(T object) {
        selectionDao.delete(object);
    }

    @Override
    public <T extends DataObject> void deleteById(Class<T> clazz, Long id) {
        selectionDao.deleteById(clazz, id);
    }

    @Override
    public <T extends DataObject> List<T> getAll(Class<T> clazz) {
        return selectionDao.getAll(clazz);
    }

    @Override
    public <T extends DataObject> List<T> listObjectByPage(Class<T> clazz, int page, int pageSize) {
        return selectionDao.listObjectByPage(clazz, page, pageSize);
    }

    @Override
    public <T extends DataObject> long countAll(Class<T> clazz) {
        return selectionDao.countAll(clazz);
    }
}
