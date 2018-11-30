package group.ydq.authority.event.impl;

import group.ydq.authority.event.Event;
import group.ydq.authority.event.EventPublisher;
import group.ydq.authority.event.Listener;
import group.ydq.authority.event.ListenerRegister;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-30 21:05
 * =============================================
 */
public class DefaultEventPublisher implements EventPublisher {
    private ListenerRegister register;

    public DefaultEventPublisher(ListenerRegister register) {
        this.register = register;
    }


    @Override
    public void fireLoginSuccessEvent(Event event) {
        for (Listener listener : register.getListeners()) {
            listener.onLoginSuccess(event);
        }
    }

    @Override
    public void fireLoginFailEvent(Event event) {
        for (Listener listener : register.getListeners()) {
            listener.onLoginFail(event);
        }
    }

    @Override
    public void fireLogoutEvent(Event event) {
        for (Listener listener : register.getListeners()){
            listener.onLogout(event);
        }
    }

    @Override
    public void fireAuthenticationSuccessEvent(Event event) {
        for (Listener listener : register.getListeners()){
            listener.onAuthenticationSuccess(event);
        }
    }

    @Override
    public void fireAuthenticationFailEvent(Event event) {
        for (Listener listener : register.getListeners()){
            listener.onAuthenticationFail(event);
        }
    }

}
