package group.ydq.service.authority;

import group.ydq.authority.event.Event;
import group.ydq.authority.event.Listener;
import group.ydq.authority.event.impl.AuthorityEvent;
import group.ydq.authority.event.impl.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-30 21:24
 * =============================================
 */
public class LogListener implements Listener {
    private Logger logger = LoggerFactory.getLogger(LogListener.class);

    @Override
    public void onLoginSuccess(Event event) {
        if (event instanceof LogEvent) {
            LogEvent e = (LogEvent) event;
            logger.info("LOGIN SUCCESS : " + e.getSubject().getPrincipal());
        }
    }

    @Override
    public void onLoginFail(Event event) {
        if (event instanceof LogEvent) {
            LogEvent e = (LogEvent) event;
            logger.info("LOGIN FAIL : " + e.getSubject().getPrincipal());
        }
    }

    @Override
    public void onLogout(Event event) {
        if (event instanceof LogEvent) {
            LogEvent e = (LogEvent) event;
            logger.info("LOGOUT : " + e.getSubject().getPrincipal());
        }
    }

    @Override
    public void onAuthenticationSuccess(Event event) {
        if (event instanceof AuthorityEvent) {
            AuthorityEvent e = (AuthorityEvent) event;
            logger.info("AUTHORITY SUCCESS : [URL:" + e.getRequest().getRequestURI() + "]");
        }
    }

    @Override
    public void onAuthenticationFail(Event event) {
        if (event instanceof AuthorityEvent) {
            AuthorityEvent e = (AuthorityEvent) event;
            logger.info("AUTHORITY FAIL : [URL:" + e.getRequest().getRequestURI() + "]");
        }
    }
}
