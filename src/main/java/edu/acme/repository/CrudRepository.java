package edu.acme.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T,Long> {

    /* Returns all entities */
    List<T> findAll();

    /* Retrieves entities by its id */
    Optional<T> findById(Long id);

    /* Deletes a given entity */
    boolean delete(T t);
    /* Updates a given entity */
    boolean update(T t);
    /* Creates a given entity */
    boolean create(T t);





}
