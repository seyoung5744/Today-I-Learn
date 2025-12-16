package com.example.springaopdemo;

public abstract class AbstractStore {

    abstract void visitedBy(User user);

    abstract boolean isVIP(User user);
}
