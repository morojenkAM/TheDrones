package ro.developmentfactory.thedrones.exception;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* ro.developmentfactory.thedrones.service.*.*(..))")
    public void logBeforeAllMethods(){
        log.info("A method in service layer is about to be executed.");
    }

}
