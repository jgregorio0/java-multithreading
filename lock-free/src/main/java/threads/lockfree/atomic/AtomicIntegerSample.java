package threads.lockfree.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerSample {
    static int initial = 0;
    static AtomicInteger atomicInt = new AtomicInteger(initial);

    static int delta = 0;
    static AtomicInteger atomicInt2 = new AtomicInteger(delta);

    public static void main(String[] args) {
        atomicInt.incrementAndGet();
        atomicInt.getAndIncrement();

        atomicInt.addAndGet(delta);
        atomicInt.getAndAdd(delta);
    }
}
