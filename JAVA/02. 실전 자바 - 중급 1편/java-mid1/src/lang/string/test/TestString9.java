package lang.string.test;

public class TestString9 {

    public static void main(String[] args) {
        String email = "hello@example.com";

        //@를 기준으로 email의 아이디 부분과 도메인을 분리
        String[] split = email.split("@");
        String id = split[0];
        String domain = split[1];
        System.out.println("ID: " + id);
        System.out.println("Domain: " + domain);
    }
}
