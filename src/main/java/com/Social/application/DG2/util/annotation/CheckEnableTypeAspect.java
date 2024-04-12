package com.Social.application.DG2.util.annotation;

import com.Social.application.DG2.entity.Enum.EnableType;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CheckEnableTypeAspect {
    @Autowired
    private UsersRepository usersRepository;
    @Before("@annotation(CheckEnableType)")
    public ResponseEntity<?> checkLogin(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Users) {
                Users user = (Users) arg;
                if (user.getEnableType() == EnableType.FALSE) {
                    return new ResponseEntity<>("Xin lỗi, không thể đăng nhập do tài khoản của bạn đã bị vô hiệu hóa.", HttpStatus.UNAUTHORIZED);
                }
            }
        }
        return null;
    }

}
