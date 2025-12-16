package com.example.springaopdemo;

import org.springframework.stereotype.Component;

@Component
public class Store extends AbstractStore {

    private String name;

    private int visitCountByUser;

    public String getOperationTime() {
        return "AM 08:00 ~ PM 08:00";
    }

    @AlarmGreetingMachine
    @Override
    public void visitedBy(User user) {
        greeting();
        visitCountByUser++;
        System.out.println("visitCountByUser = " + visitCountByUser);
    }

    @Override
    public boolean isVIP(User user) {
        return visitCountByUser > 10;
    }

    public void setVisitCountByUser(int visitCountByUser) {
        this.visitCountByUser = visitCountByUser;
    }

    private void greeting() {
        System.out.println("어서 오세요!");
//        IGreetingMachine greetingMachine = new GreetingMachineProxy(new GreetingMachine());
//        greetingMachine.greet(user);

//        IGreetingMachine greetingMachine = new DancingGreetingMachineDecorator(new AlarmGreetingMachineDecorator(new GreetingMachine()));
//        greetingMachine.greet(user);
    }
}
