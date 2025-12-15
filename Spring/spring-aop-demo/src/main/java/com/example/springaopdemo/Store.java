package com.example.springaopdemo;

public class Store {

    private String name;

    public String getOperationTime() {
        return "AM 08:00 ~ PM 08:00";
    }

    public void visitedBy(User user) {
        greeting(user);
    }

    private void greeting(User user) {
        IGreetingMachine greetingMachine = new GreetingMachineProxy(new GreetingMachine());
        greetingMachine.greet(user);
    }
}
