import java.util.Scanner;

public class Main {

    public static Scanner in = new Scanner(System.in);
    public static User user = new User();

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("1 : 회원가입 \t 2 : 로그인 \t 3 : 종료 ");
            System.out.print(">>>>>>  ");

            String n = in.nextLine();

            if (n.equals("1")) {
                make_user();
            } else if (n.equals("2")) {
                longin();
            } else if (n.equals("3")) {
                break;
            } else if (n.equals("admin")) {
                System.out.println("관리자 접근");
                user.get_DB();
            } else {
                System.out.println("잘못 입력");
            }
            System.out.println();
        }
    }

    static void make_user() throws Exception {
        System.out.print("아이디 입력\n>>>>>>  ");
        String id = in.nextLine();
        System.out.print("비밀번호 입력\n>>>>>>  ");
        String pass = in.nextLine();
        user.setUser(id, pass.getBytes());
    }

    static void longin() throws Exception {
        System.out.print("아이디 입력\n>>>>>>  ");
        String id = in.nextLine();
        System.out.print("비밀번호 입력\n>>>>>>  ");
        String pass = in.nextLine();

        user.getUser(id, pass.getBytes());
    }

}
