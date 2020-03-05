package webTest;
import java.math.BigDecimal;

public class Fibonacci {
    public static void main(String[] args) {
        for (int i = 1; i < 200; i++) {
            System.out.println(of(i));
        }
    }

    static BigDecimal of(int num) {
        if (num == 1 || num == 2) return BigDecimal.valueOf(1);
        else return of(num - 1).add(of(num - 2));

    }
}