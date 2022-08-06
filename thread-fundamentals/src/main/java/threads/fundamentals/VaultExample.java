package threads.fundamentals;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VaultExample {
    public static final int MAX_PASS = 9999;

    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();
        Vault vault = new Vault(r.nextInt(MAX_PASS));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());
        for (Thread thread : threads) {
            thread.start();
        }
    }

    static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        private boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
            return this.password == guess;
        }
    }

    static class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
        }
    }

    private static class AscendingHackerThread extends HackerThread {
        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_PASS; i++) {
                if (vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " guessed pass " + i);
                    System.exit(0);
                }

            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = MAX_PASS; i >= 0; i--) {
                if (vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " guessed pass " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                }
                System.out.println(i);

            }
            System.out.println("Game over hackers");
            System.exit(0);
        }

    }


}
