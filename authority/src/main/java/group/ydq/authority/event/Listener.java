package group.ydq.authority.event;

public interface Listener {
    void onLoginSuccess(Event event);

    void onLoginFail(Event event);

    void onLogout(Event event);

    void onAuthenticationSuccess(Event event);

    void onAuthenticationFail(Event event);
}
