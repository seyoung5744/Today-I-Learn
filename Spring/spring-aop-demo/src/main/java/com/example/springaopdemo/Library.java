package com.example.springaopdemo;

import org.springframework.stereotype.Component;

@Component
public class Library extends AbstractStore {

    private String name;

    private int visitCountByUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @AlarmGreetingMachine
    @Override
    public void visitedBy(User user) {
        System.out.println("환영합니다! " + name + " 입니다.");
    }

    @Override
    public boolean isVIP(User user) {
        return visitCountByUser > 10;
    }

    public void setVisitCountByUser(int visitCountByUser) {
        this.visitCountByUser = visitCountByUser;
    }
}
