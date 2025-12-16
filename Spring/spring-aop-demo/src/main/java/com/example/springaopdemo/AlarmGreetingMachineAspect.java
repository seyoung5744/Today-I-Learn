package com.example.springaopdemo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AlarmGreetingMachineAspect {

    @Before(value = "@annotation(AlarmGreetingMachine)")
    public void alarm(JoinPoint joinPoint) {
        AbstractStore abstractStore = (AbstractStore) joinPoint.getTarget();
//        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
//        if ("Store".equals(simpleName)) {
//            Store store = (Store) joinPoint.getTarget();
//            if (store.isVIPUser()) {
//                System.out.println("VIP 유저 입니다! 사은품을 받아가세요!");
//            }
//        } else if ("Library".equals(simpleName)) {
//            Library library = (Library) joinPoint.getTarget();
//            if (library.isVIPUser()) {
//                System.out.println("VIP 유저 입니다! 사은품을 받아가세요!");
//            }
//        }

        Object[] args = joinPoint.getArgs();

        User user = (User) args[0];
        String username = user.getName();

        boolean isVip = abstractStore.isVIP(user);
        if (isVip) {
            System.out.println("VIP 유저 입니다! 사은품을 받아가세요!");
        }
        System.out.println(username + "이(가) 방문했습니다. ");
    }
}
