package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    public void printMethod() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName("가장 정확한 포인트컷")
    public void exactMatch() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("가장 많이 생략한 포인트컷")
    public void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Nested
    @DisplayName("메서드 이름 매칭 관련 포인트컷")
    class NameMatch {

        @Test
        @DisplayName("메서드 이름 정확히 매칭")
        public void nameMatch() {
            pointcut.setExpression("execution(* hello(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        @DisplayName("* 하나 사용")
        public void nameMatchStar1() {
            pointcut.setExpression("execution(* hel*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        @DisplayName("* 두개 사용")
        public void nameMatchStar2() {
            pointcut.setExpression("execution(* *el*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        @DisplayName("이름 매칭 실패")
        public void nameMatchFalse() {
            pointcut.setExpression("execution(* nano(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
        }
    }

    @Nested
    @DisplayName("패키지 매칭 관련 포인트컷")
    class PackageMatch {

        @Test
        public void packageExactMatch1() {
            pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        public void packageExactMatch2() {
            pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        public void packageExactFalse() {
            pointcut.setExpression("execution(* hello.aop.*.*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
        }

        @Test
        public void packageMatchSubPackage1() {
            pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        public void packageMatchSubPackage2() {
            pointcut.setExpression("execution(* hello.aop..*.*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }
    }

    @Nested
    @DisplayName("타입 매칭")
    class TypeMatch {

        @Test
        public void typeExactMatch() {
            pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        public void typeMatchSuperClass() {
            pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        @Test
        public void typeMatchInternal1() throws NoSuchMethodException {
            pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

            Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
            assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
        }

        //포인트컷으로 지정한 MemberService 는 internal 이라는 이름의 메서드가 없다.
        @Test
        public void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
            pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

            Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
            assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
        }
    }

    @Nested
    @DisplayName("파라미터 매칭")
    class ParameterMatch {

        // String 타입의 파라미터 허용
        // (String)
        @Test
        public void argsMatch() {
            pointcut.setExpression("execution(* *(String))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        // 파라미터가 없어야 함
        // ()
        @Test
        public void argsMatchNoArgs() {
            pointcut.setExpression("execution(* *())");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
        }

        // 정확히 하나의 파라미터 허용, 모든 타입 허용
        // (Xx)
        @Test
        public void argsMatchStar() {
            pointcut.setExpression("execution(* *(*))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        // 숫자와 무관하게 모든 파라미터, 모든 타입 허용
        // (), (Xxx), (Xxx, Xxx)
        @Test
        public void argsMatchAll() {
            pointcut.setExpression("execution(* *(..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }

        // String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
        // (String), (String, Xxx), (String, Xxx, Xxx)
        @Test
        public void argsMatchComplex() {
            pointcut.setExpression("execution(* *(String, ..))");
            assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        }
    }
}
