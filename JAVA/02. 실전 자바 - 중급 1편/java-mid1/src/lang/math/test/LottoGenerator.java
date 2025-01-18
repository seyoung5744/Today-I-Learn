package lang.math.test;

import java.util.Random;

public class LottoGenerator {

    private final Random random = new Random();
    private int[] lottoNumbers;

    public int[] generate() {
        lottoNumbers = new int[6];
        int count = 0;

        while (count < 6) {
            int number = random.nextInt(45) + 1;

            if (isNotUnique(number)) {
                continue;
            }

            lottoNumbers[count] = number;
            count++;
        }

        return lottoNumbers;
    }

    private boolean isNotUnique(int number) {
        for (int lottoNumber : lottoNumbers) {
            if (number == lottoNumber) {
                return true;
            }
        }
        return false;
    }
}
