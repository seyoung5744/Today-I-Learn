import java.util.ArrayList;
import java.util.List;

public class DB {

    private static List<String[]> userData = new ArrayList<>();

    // 유저 생성
    public void setUser(String id, String hashingPassword, String salt) {
        String[] user = {id, hashingPassword, salt};
        userData.add(user);
    }

    // 들어온 ID 와 비밀번호가 일치하는지 체크
    public boolean check(String id, String hashingPassword) {
        for (int i = 0; i < userData.size(); i++) {
            if (id.equals(userData.get(i)[0]) && hashingPassword.equals(userData.get(i)[1])) {
                return true;
            }
        }
        return false;
    }

    // 해당 ID 와 SALT 값 찾기
    public String getSalt(String id) {
        for (int i = 0; i < userData.size(); i++) {
            if (id.equals(userData.get(i)[0])) {
                return userData.get(i)[2];
            }
        }
        return null; // 아이디가 존재하지 않을 경우 null 반환
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] user : userData) {
            sb.append("ID : " + user[0] + "\tPassword : " + user[1] + "\tSALT : " + user[2]).append("\n\n");
        }
        return sb.toString();
    }
}
