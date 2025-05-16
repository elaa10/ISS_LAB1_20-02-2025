package ro.iss2025.utils.events;

import ro.iss2025.domain.Exemplar;

public class AvailabilityEvent implements Event {
    private final Exemplar exemplar;

    public AvailabilityEvent(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }
}
