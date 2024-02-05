package chap09._9_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class _9_1_5_ExecuteAround {

    private static final String FILE = _9_1_5_ExecuteAround.class.getResource("./data.txt").getFile();

    public static void main(String[] args) throws IOException {
        // 더 유연하게 리팩토링할 메서드
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("===================\n");

        String oneLine = processFile((BufferedReader br) -> br.readLine());
        System.out.println(oneLine);

        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(twoLine);
    }

    public static String processFileLimited() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return br.readLine();
        }
    }
    
    public static String processFile(BufferedReaderProcessor p) throws IOException {    
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return p.process(br);
        }
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {

        String process(BufferedReader b) throws IOException;
    }

}
