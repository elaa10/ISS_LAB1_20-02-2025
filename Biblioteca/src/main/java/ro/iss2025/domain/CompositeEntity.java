package ro.iss2025.domain;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class CompositeEntity<T> implements Serializable {
    public abstract T getId();
    public abstract void setId(T id);
}
