package DAO;

import models.Department;

import java.util.List;

public interface DAO<T> {
    void getAll();

    T getById(int id);

    T create(T entity);

    T update(T entity);

    void delete(int id);

}
