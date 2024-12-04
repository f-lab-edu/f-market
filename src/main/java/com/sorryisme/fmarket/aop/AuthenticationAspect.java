package com.sorryisme.fmarket.aop;

import com.sorryisme.fmarket.common.SessionManager;
import com.sorryisme.fmarket.exception.RequireLoginException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class AuthenticationAspect {


    private final SessionManager sessionManager;

    @Before("@annotation(com.sorryisme.fmarket.annotation.RequireLogin)")
    public boolean authenticate() {

        Long loginId = sessionManager.getUserId();

        if (loginId == null) {
            throw new RequireLoginException("로그인에 실패했습니다. 로그인해주세요");
        }

        return true;
    }
}
