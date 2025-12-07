package com.example.springbootmetrics.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private AtomicLong counter = new AtomicLong();
    
    @GetMapping("/hello")
    public String world() {
        counter.incrementAndGet();
        return "world";
    }

    @GetMapping("/mon/counter")
    public long getCount() {
        return counter.get();
    }
}
