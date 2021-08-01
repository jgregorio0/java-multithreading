import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JoinInterruptExample {

    public static BigInteger base1 = new BigInteger("10");
    public static BigInteger power1 = new BigInteger("1");
    public static BigInteger base2 = new BigInteger("20");
    public static BigInteger power2 = new BigInteger("9");
    public static long timeout = 10;

    public static void main(String[] args) {
        JoinInterruptExample sol = new JoinInterruptExample();
        try {
            BigInteger res = sol.calculateResult(base1, power1, base2, power2);
            System.out.println("CompleCalculationSolution " + base1 + ", " + power1 + ", " + base2 + ", " + power2 + " = " + res);
        } catch (Exception e) {
            System.out.println("Proceso interrumpido por superar " + timeout + " ms para " + base1 + ", " + power1 + ", " + base2 + ", " + power2);
        }
    }

    public BigInteger calculateResult(BigInteger base1,
                                      BigInteger power1,
                                      BigInteger base2,
                                      BigInteger power2) throws Exception {
        List<PowerCalculatingThread> threads = new ArrayList<>(2);//TODO
        threads.add(new PowerCalculatingThread(base1, power1));
        threads.add(new PowerCalculatingThread(base2, power2));

        for (PowerCalculatingThread thread : threads) {
            thread.start();
        }

        for (PowerCalculatingThread thread : threads) {
            try {
                thread.join(timeout);
                thread.interrupt();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception");
                e.printStackTrace();
            }
        }

        BigInteger result = BigInteger.ZERO;
        for (PowerCalculatingThread thread : threads) {
            if (thread.isInterrupted()) {
                throw new Exception("Proceso interrumpido");
            }
            result = result.add(thread.getResult());
        }

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO;
                 i.compareTo(power) != 0;
                 i = i.add(BigInteger.ONE)) {
                if (currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation");
                    result = BigInteger.ZERO;
                    break;
                }
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() {
            return result;
        }
    }
}