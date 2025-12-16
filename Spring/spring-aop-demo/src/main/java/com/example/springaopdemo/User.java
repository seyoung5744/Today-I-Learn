package com.example.springaopdemo;

public class User {

    private String name;
    private String email;

    public String greeting() {
        return "hello";
    }

    public void visitTo(Store store) {
        // store에 user 방문을 알림.
        store.visitedBy(this);
    }

    public void visitTo(Library library) {
        // store에 user 방문을 알림.
        library.visitedBy(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
