import java.security.MessageDigest;
import java.security.SecureRandom;

public class User {

    private static final int SALT_SIZE = 16;
    private static DB db = new DB();

    // 새로운 계정 만들기
    public void setUser(String id, byte[] password) throws Exception {
        String salt = getSalt();
        db.setUser(id, hashing(password, salt), salt);
    }

    // 유저 정보와 대조한 뒤 로그인 하기
    public void getUser(String id, byte[] password) throws Exception {
        String tempSalt = db.getSalt(id); // 해당 ID 의 SALT 값 찾기.
        String tempPass = hashing(password, tempSalt); // 얻어온 Salt 와 password 조합

        if (db.check(id, tempPass)) { // db 에 저장된 아이디와 비밀번호를 대조
            System.out.println("로그인 성공!!");
            return;
        }

        System.out.println("로그인 실패!!");
    }

    // 비밀번호 해싱
    private String hashing(byte[] password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256 해시함수를 사용

        // key-stretching
        for (int i = 0; i < 10000; i++) {
            String temp = byteToString(password) + salt; // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.getBytes());
            password = md.digest();
        }

        return byteToString(password);
    }

    // SALT 값 생성
    private String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        secureRandom.nextBytes(temp);

        return byteToString(temp);
    }

    // 바이트 값을 16진수로 변경해준다
    private String byteToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

    public void get_DB() {
        System.out.println("\n================DB출력================\n");
        System.out.println(db);
    }
}
