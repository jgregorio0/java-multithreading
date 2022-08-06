package multithread.intercomm.object;

public class ConditionWithObject {

    static String text;
    static Object lockObject = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            waitingForCondition();
        });

        Thread t2 = new Thread(() -> {
            releaseCondition();
        });

        t1.start();
        t2.start();
    }

    private static void releaseCondition() {
        System.out.println("releaseCondition");
        System.out.println("releaseCondition - lock");
        synchronized (lockObject) {
            try {
                System.out.println("releaseCondition - text = hola");
                text = "hola";
                System.out.println("releaseCondition - notify");
                lockObject.notify();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("releaseCondition - unlock");
            }
        }

    }

    private static void waitingForCondition() {
        System.out.println("waitingForCondition");
        synchronized (lockObject) {
            System.out.println("waitingForCondition - lock");
            try {
                while (text == null) {
                    System.out.println("waitingForCondition - test null");
                    System.out.println("waitingForCondition - wait");
                    lockObject.wait();
                }
                System.out.println("waitingForCondition - text NOT null");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("waitingForCondition - unlock");
            }
        }
    }
}
