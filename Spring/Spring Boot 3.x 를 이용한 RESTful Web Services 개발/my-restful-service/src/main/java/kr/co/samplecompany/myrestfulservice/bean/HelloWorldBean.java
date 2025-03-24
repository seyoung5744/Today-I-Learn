package kr.co.samplecompany.myrestfulservice.bean;

import lombok.Data;

@Data
public class HelloWorldBean {

    private final String message;

    public HelloWorldBean(String message) {
        this.message = message;
    }
}
