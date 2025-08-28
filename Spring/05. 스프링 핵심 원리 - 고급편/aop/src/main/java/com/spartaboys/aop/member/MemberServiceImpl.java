package com.spartaboys.aop.member;

import com.spartaboys.aop.member.annotaion.ClassAop;
import com.spartaboys.aop.member.annotaion.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }
}
