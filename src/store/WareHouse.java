package store;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class WareHouse {

    private final List<Integer> products = new ArrayList<>();
    private final StampedLock stampedLock = new StampedLock();

    public int consulStockOfProduct(int id) {
        long stamp = stampedLock.tryOptimisticRead();
        int countproduct = countProductById(id);

        if (!stampedLock.validate(stamp)) {
            stampedLock.readLock();
            try {
                return countProductById(id);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }

        return countproduct;
    }

    public void addStockProduct(int id) {
        Long stamp = stampedLock.writeLock();
        try {
            products.add(id);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    private int countProductById(int id) {
        int count = 0;

        for (Integer product : products) {
            if (id == product) {
                count++;
            }
        }
        return count;
    }
}
