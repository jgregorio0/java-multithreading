package threads.datasharing;

public class Stack {
    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        int res = sum(1, 2);
    }

    private static int sum(int a, int b) {
        int s = a + b;
        return s;
    }
}
