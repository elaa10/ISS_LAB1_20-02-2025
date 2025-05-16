package ro.iss2025.repository;

import ro.iss2025.domain.CompositeEntity;

public interface CompositeRepository<ID, E extends CompositeEntity<ID>> {
    E findOne(ID id);
    Iterable<E> findAll();
    boolean save(E entity);
    boolean delete(ID id);
    boolean update(E entity);
}
