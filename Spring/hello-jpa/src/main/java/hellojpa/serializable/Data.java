package hellojpa.serializable;

import java.io.Serializable;

public class Data implements Serializable {
//    private static final long serialVersionUID = -8798205991877440851L;

    private int no;
    private String name;
    private String email;

    public Data(int no, String name, String email) {
        this.no = no;
        this.name = name;
        this.email = email;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
