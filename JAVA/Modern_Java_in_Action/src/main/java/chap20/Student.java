package chap20;

public class Student {

    private String name;
    private int id;

    public static void main(String[] args) {
        Student student = new Student("Raoul", 1);
        System.out.println(student.name);
        student.id = 1337;
        System.out.println(student.id);
    }
    public Student(){}

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
