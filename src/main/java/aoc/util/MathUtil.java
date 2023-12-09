package aoc.util;

import java.math.BigInteger;
import java.util.List;

public final class MathUtil {
    private MathUtil() {
        // EMPTY
    }

    public static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }

    public static long findLCM(List<Long> numbers) {
        BigInteger result = BigInteger.valueOf(numbers.get(0));

        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, BigInteger.valueOf(numbers.get(i)));
        }

        return result.longValue();
    }
}
