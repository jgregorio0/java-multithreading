package multithread.intercomm.conditionvariable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionWithLock {

    static String text;
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

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
        lock.lock();
        try {
            System.out.println("releaseCondition - text = hola");
            text = "hola";
            System.out.println("releaseCondition - signal");
            condition.signal();
        } finally {
            System.out.println("releaseCondition - unlock");
            lock.unlock();
        }
    }

    private static void waitingForCondition() {
        System.out.println("waitingForCondition");
        System.out.println("waitingForCondition - lock");
        lock.lock();
        try {
            while (text == null) {
                System.out.println("waitingForCondition - test is null");
                System.out.println("waitingForCondition - wait");
                condition.await();
            }
            System.out.println("waitingForCondition - text NOT null");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("waitingForCondition - unlock");
            lock.unlock();
        }
    }
}
