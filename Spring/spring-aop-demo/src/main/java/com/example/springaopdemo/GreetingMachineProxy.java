package com.example.springaopdemo;

public class GreetingMachineProxy implements IGreetingMachine {

    private final IGreetingMachine target;

    public GreetingMachineProxy(IGreetingMachine target) {
        this.target = target;
    }

    @Override
    public void greet(User user) {
        // 부가
        System.out.println(user.getName() + "이(가) 방문했습니다.");

        target.greet(user);
    }
}
