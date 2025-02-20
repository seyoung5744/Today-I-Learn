package thread.collection.simple;

public class SimpleListMainV1 {

    public static void main(String[] args) {
        SimpleList list = new BasicList();

        // 동작 확인
        list.add("A");
        list.add("B");
        System.out.println("list = " + list);
    }
}
