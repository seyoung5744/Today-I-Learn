package hellojpa.serializable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamEx {

    public static void main(String[] args) {
//        ObjectOutputStream oos = null;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("obj.txt"))){
//            oos = new ObjectOutputStream(new FileOutputStream("obj.txt"));
            Data data = new Data(1, "seyoung", "seyoung@naver.com");
            oos.writeObject(data);
            System.out.println("입력 완료");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } finally {
//            try {
//                if(oos != null) {
//                    oos.close();
//                }
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
