package wonspring.learningtest.archunit.adapter;

import wonspring.learningtest.archunit.application.MyService;

public class MyAdapter {

    MyService myService;

    void run() {
        myService = new MyService();
        System.out.println("myService = " + myService);
    }
}
