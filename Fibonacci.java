class Fibonacci {
    public static void main(String[] args) {
        for (int i = 1; i < 200; i++) {
            System.out.println(of(i));
        }
    }

    static int of(int num) {
        if (num == 1 || num == 2) return num;
        else return of(num - 1) + of(num - 2);

    }
}