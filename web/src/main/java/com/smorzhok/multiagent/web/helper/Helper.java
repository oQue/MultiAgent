package com.smorzhok.multiagent.web.helper;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author Dmitry Smorzhok
 */
public class Helper {

    private Helper() {}

    public static String getCurrentSession() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fcontext.getExternalContext().getSession(false);
        return session.getId();
    }

}
