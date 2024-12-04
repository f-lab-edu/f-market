package com.sorryisme.fmarket.common;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionManager {

    private static final String LOGIN_ID = "LOGIN_ID";
    private final HttpSession session;

    public Long getUserId() {
        return (Long) session.getAttribute(LOGIN_ID);
    }

    public void setLoginUserId(Long id) {
        session.setAttribute(LOGIN_ID, id);
    }

    public void removeLoginUserId() {
        session.removeAttribute(LOGIN_ID);
    }

}
