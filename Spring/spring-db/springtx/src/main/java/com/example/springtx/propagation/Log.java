package com.example.springtx.propagation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    protected Log() {
    }

    public Log(String message) {
        this.message = message;
    }
}
