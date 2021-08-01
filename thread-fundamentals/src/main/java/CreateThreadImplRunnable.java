public class CreateThreadImplRunnable {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("We are in thread " + Thread.currentThread().getName() + " after start a new thread");
            System.out.println("Current priority is " + Thread.currentThread().getPriority());
            throw new RuntimeException("error de ejemplo");
        });
        t.setName("New worker Thread");
        t.setPriority(Thread.MAX_PRIORITY);

        t.setUncaughtExceptionHandler((thread, throwable) -> {
            System.out.println("A critical error happened in thread " + thread.getName() + " the error is " + throwable.getMessage());
        });

        System.out.println("We are in thread " + Thread.currentThread().getName() + " before start a new thread");
        t.start();
        System.out.println("We are in thread " + Thread.currentThread().getName() + " after start a new thread");

        Thread.sleep(10000);
    }
}
