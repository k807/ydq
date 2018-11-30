package group.ydq.authority.event;

public interface EventPublisher {
    void fireLoginSuccessEvent(Event event);

    void fireLoginFailEvent(Event event);

    void fireLogoutEvent(Event event);

    void fireAuthenticationSuccessEvent(Event event);

    void fireAuthenticationFailEvent(Event event);
}
