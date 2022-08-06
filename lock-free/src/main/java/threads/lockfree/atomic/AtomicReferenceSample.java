package threads.lockfree.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceSample {
    static String oldName = "old name";
    static String newName = "new name";

    public static void main(String[] args) {
        AtomicReference<String> atomicReference = new AtomicReference<>(oldName);

        atomicReference.set("Unexpected name");
        if (atomicReference.compareAndSet(oldName, newName)) {
            System.out.println("New Value is " + atomicReference.get());
        } else {
            System.out.println("Nothing change");
        }
    }
}
