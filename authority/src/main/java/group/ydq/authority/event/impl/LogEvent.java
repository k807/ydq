package group.ydq.authority.event.impl;

import group.ydq.authority.Subject;
import group.ydq.authority.event.Event;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-30 21:17
 * =============================================
 */
public class LogEvent implements Event {
    private String msg;

    private Subject subject;

    public LogEvent(Subject subject, String msg) {
        this.subject = subject;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Subject getSubject() {
        return subject;
    }
}
