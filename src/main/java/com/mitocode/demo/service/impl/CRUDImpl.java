package com.mitocode.demo.service.impl;

import com.mitocode.demo.exception.ModelNotFoundException;
import com.mitocode.demo.model.Patient;
import com.mitocode.demo.repo.IGenericRepo;
import com.mitocode.demo.service.ICRUD;

import java.util.List;

public abstract class CRUDImpl<T,ID>  implements ICRUD<T,ID> {

    protected  abstract IGenericRepo<T,ID> getRepo();

    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(()->new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().deleteById(id);
    }
}
