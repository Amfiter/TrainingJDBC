package DAO;

import models.Department;

import java.util.List;

public interface DAO<T> {
    public T getById(T id);
    public  Object create(T entity);
    public  T update(T entity);
    public  boolean delete(T id);;

 }
