package group.ydq.authority.event;

import java.util.List;

public interface ListenerRegister {

    List<Listener> getListeners();

    Listener getListenerByName(String name);

    void registListener(String name, Listener listener);
}
