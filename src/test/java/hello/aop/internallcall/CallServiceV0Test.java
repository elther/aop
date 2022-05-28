package hello.aop.internallcall;

import hello.aop.internallcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
public class CallServiceV0Test {

    @Autowired CallServiceV0 callServiceV0;

    @Test
    void external(){
        callServiceV0.external();
        log.info("target={}", callServiceV0.getClass());
    }

    @Test
    void internal(){
        callServiceV0.internal();
    }
}
