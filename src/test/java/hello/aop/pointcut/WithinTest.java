package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact(){
        pointcut.setExpression("within(hello.aop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinExactStar(){
        pointcut.setExpression("within(hello.aop.member.*Service*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage(){
        pointcut.setExpression("within(hello.aop..*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타겟 타입에만 직접 적용, 인터페이스 선정 안됨")
    void withinSuperTypeFalse(){
        pointcut.setExpression("within(hello.aop.member.MemberService))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("인터페이스 적용 가능")
    void executionSuperTypeTrue(){
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..)))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
