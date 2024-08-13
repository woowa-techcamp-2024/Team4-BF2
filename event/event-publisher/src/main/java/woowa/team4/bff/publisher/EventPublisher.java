package woowa.team4.bff.publisher;

import woowa.team4.bff.event.Event;

public interface EventPublisher {

    void publish(Event event);
}
