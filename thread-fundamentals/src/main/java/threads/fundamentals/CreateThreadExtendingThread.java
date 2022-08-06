package threads.fundamentals;

public class CreateThreadExtendingThread {
    public static void main(String[] args) throws InterruptedException {
        Thread rt = new RunableThread();
        rt.start();
    }

    static class RunableThread extends Thread {
        @Override
        public void run() {
            System.out.println("We are in thread " + Thread.currentThread().getName());
        }
    }
}
