package ait.cohort5860.post.service.logging;

import ait.cohort5860.post.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

@Service // компонент Spring — будет автоматически обнаружен
@Slf4j(topic = "Post Service") // подключение логгера SLF4J
@Aspect // указывает, что класс — аспект AOP
public class PostServiceLogger {

    @Pointcut("execution(public * ait.cohort5860.post.service.PostServiceImpl.*(Long)) && args(id)") // перехват всех public методов с аргументом Long
    public void findById(Long id) {
    }

    @Pointcut("@annotation(ait.cohort5860.post.service.logging.PostLogger)") // перехват методов, аннотированных @PostLogger
    public void annotatePostLogger() {
    }

    @Pointcut("execution(public Iterable<ait.cohort5860.post.dto.PostDto> ait.cohort5860.post.service.PostServiceImpl.findPosts*(..))") // все методы findPosts* с возвращаемым Iterable<PostDto>
    public void bulkFindPostsLogger() {
    }

    @Before("findById(id)") // выполняется до метода, указавшего Long id
    public void logFindById(Long id) {
        log.info("Find post by id: {}", id); // логируем вызов метода поиска по id
    }

    @AfterReturning("annotatePostLogger()") // после успешного выполнения метода с @PostLogger
    public void logAnnotatePostLogger(JoinPoint joinPoint) {
        log.info("Annotate by PostLogger method: {}, done", joinPoint.getSignature().getName()); // логируем имя метода
    }

    @Around("bulkFindPostsLogger()") // обертываем вызов метода поиска по фильтру
    public Object logBulkFindPostsLogger(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs(); // получаем аргументы метода

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String str) {
                args[i] = str.toLowerCase(); // приводим все строки к нижнему регистру
            }
        }

        long start = System.currentTimeMillis(); // фиксируем начало выполнения
        Object result = joinPoint.proceed(args); // выполняем метод с новыми аргументами
        long end = System.currentTimeMillis(); // фиксируем конец выполнения

        log.info("method: {}, time: {} ms", joinPoint.getSignature().getName(), end - start); // логируем имя и время выполнения
        return result; // возвращаем результат метода
    }
}
