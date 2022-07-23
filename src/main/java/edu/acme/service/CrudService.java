package edu.acme.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T,Long> {
    List<T> findAll();

    Optional<T> findByID(Long id);

    boolean delete(T t);

    void create(T t);

    boolean update(T t);
}
