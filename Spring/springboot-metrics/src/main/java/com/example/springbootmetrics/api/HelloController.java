package com.example.springbootmetrics.api;

import com.example.springbootmetrics.service.RequestOrderUsecase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private AtomicLong counter = new AtomicLong();
    private final Random random = new Random();
    private final RequestOrderUsecase requestOrderUsecase;

    public HelloController(RequestOrderUsecase requestOrderUsecase) {
        this.requestOrderUsecase = requestOrderUsecase;
    }

    @GetMapping("/hello/{percent}")
    public String world(@PathVariable Integer percent) throws InterruptedException {
        if (random.nextInt(100) < percent) {
            int sleepTime = 100 * random.nextInt(500);
            TimeUnit.MICROSECONDS.sleep(sleepTime);
            throw new RuntimeException("delayed");
        }
        counter.incrementAndGet();
        return "world";
    }

    @GetMapping("/mon/counter")
    public long getCount() {
        return counter.get();
    }

    @GetMapping("/order")
    public void order() {
        requestOrderUsecase.requestOrder();
    }
}
