package ro.iss2025.utils.observer;

import ro.iss2025.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E event);
}