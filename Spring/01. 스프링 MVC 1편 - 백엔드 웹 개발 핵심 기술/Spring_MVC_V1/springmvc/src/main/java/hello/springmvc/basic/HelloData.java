package hello.springmvc.basic;

public class HelloData {

    private String username;
    private int age;

    public HelloData() {
        System.out.println("HelloData.HelloData");
    }

//    public HelloData(String username, int age) {
//        System.out.println("HelloData.HelloData(String, int)");
//        this.username = username;
//        this.age = age;
//    }

    public String getUsername() {
        System.out.println("HelloData.getUsername");
        return username;
    }

    public void setUsername(String username) {
        System.out.println("HelloData.setUsername");
        this.username = username;
    }

    public int getAge() {
        System.out.println("HelloData.getAge");
        return age;
    }

    public void setAge(int age) {
        System.out.println("HelloData.setAge");
        this.age = age;
    }
}
