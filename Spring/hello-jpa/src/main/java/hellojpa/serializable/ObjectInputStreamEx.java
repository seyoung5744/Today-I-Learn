package hellojpa.serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamEx {

    public static void main(String[] args) {
        ObjectInputStream ois = null;
        try {
            // 파일에 저장된 객체를 읽어들이기 위해 ObjectInputStream 객체 생성
            ois = new ObjectInputStream(new FileInputStream("obj.txt"));

            // ObjectInputStream 은 스트림으로 부터 읽어들인 직렬화된 객체를 역직렬화해서 객체를 생성할 수 있다.
            // 역직렬화할때 필요한 클래스 파일을 찾지 못할 경우
            // ClassNotFoundException 을 발생시킴.
            Data data = (Data) ois.readObject();
            System.out.println("번호 : " + data.getNo());
            System.out.println("이름 : " + data.getName());
            System.out.println("메일 : " + data.getEmail());
            ois.reset();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
