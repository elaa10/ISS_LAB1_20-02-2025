package ro.iss2025.repository;

import ro.iss2025.domain.Entity;


public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> findAll();

    boolean save(E entity);

    boolean delete(ID id);

    boolean update(E entity);
}
