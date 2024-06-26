package hello.aop.proxyvs;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    public void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 -> 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 -> 실패, ClassCastException 예외 발생
        assertThatThrownBy(() -> {
                MemberServiceImpl castingMemberService = (MemberServiceImpl) proxyFactory.getProxy();
            }
        ).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 프록시

        // 프록시를 인터페이스로 캐스팅 -> 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // CGLIB 프록시를 구현 클래스로 캐스팅 시도 -> 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) proxyFactory.getProxy();
    }
}
