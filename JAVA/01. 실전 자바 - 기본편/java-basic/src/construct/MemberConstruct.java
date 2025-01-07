package construct;

public class MemberConstruct {

    String name;
    int age;
    int grade;

    // 추가
    MemberConstruct(String name, int age) {
//        System.out.println("go");
        this(name, age, 50); // Call to 'this()' must be first statement in constructor body
//        this.name = name;
//        this.age = age;
//        this.grade = 50;
    }

    MemberConstruct(String name, int age, int grade) {
        System.out.println("생성자 호출 name=" + name + ", age=" + age + ", grade=" + grade);
        this.name = name;
        this.age = age;
        this.grade = grade;
    }
}
