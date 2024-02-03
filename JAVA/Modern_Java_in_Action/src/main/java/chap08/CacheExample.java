package chap08;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 8.3.4 계산 패턴
public class CacheExample {

    private MessageDigest messageDigest;

    public static void main(String[] args) {
        new CacheExample().main();
    }

    public CacheExample() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void main() {
        List<String> lines = Arrays.asList(
            " Nel   mezzo del cammin  di nostra  vita ",
            "mi  ritrovai in una  selva oscura",
            " che la  dritta via era   smarrita "
        );
        Map<String, byte[]> dataToHash = new HashMap<>();
        lines.forEach(line ->
            dataToHash.computeIfAbsent(line, // line 은 맵에서 찾을 키
                this::calculateDigest)// 키가 존재하지 않으면 동작을 실행
        );

        dataToHash.forEach((line, hash) ->
            System.out.printf("%s -> %s%n", line,
                new String(hash).chars()
                    .map(i -> i & 0xff) // https://velog.io/@gkswl412/java-0xff-xl5bsrwm
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(", ", "[", "]"))
            )
        );

        int a = 150;
        System.out.println(a + " => " + Integer.toString(a, 2));

        byte b = (byte) a;
        System.out.println(b + " => " + Integer.toString(b, 2));
        System.out.println(b & 0xff);
    }

    private byte[] calculateDigest(String key) {
        return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
    }
}
